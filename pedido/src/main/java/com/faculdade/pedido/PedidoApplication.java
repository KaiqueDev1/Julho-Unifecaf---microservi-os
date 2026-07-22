package com.faculdade.pedido;

import org.springframework.beans.factory.annotation.Value; // IMPORTANTE: Nova importação adiconada
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class PedidoApplication {
	public static void main(String[] args) {
		SpringApplication.run(PedidoApplication.class, args);
	}
}

@Configuration
class RestClientConfig {
	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}
}

@RestController
@RequestMapping("/api/pedidos")
class PedidosController {

	private final RestClient restClient;

	// NOVO: Captura as URLs dinamicamente. Se não achar a variável de ambiente, usa o localhost por padrão.
	@Value("${URL_ESTOQUE:http://localhost:8083}")
	private String urlEstoque;

	@Value("${URL_PAGAMENTOS:http://localhost:8082}")
	private String urlPagamentos;

	public PedidosController(RestClient restClient) {
		this.restClient = restClient;
	}

	@PostMapping
	public PedidoResponse criarPedido(@RequestBody PedidoRequest request) {

		// ALTERADO: Trocado 'http://localhost:8083' pela variável dinâmica 'urlEstoque'
		var estoqueBody = new ReservaRequest(request.produtoId(), request.quantidade());
		EstoqueResponse estoqueResponse = restClient.post()
				.uri(urlEstoque + "/api/estoque/reserva")
				.body(estoqueBody)
				.retrieve()
				.body(EstoqueResponse.class);

		// ALTERADO: Trocado 'http://localhost:8082' pela variável dinâmica 'urlPagamentos'
		var pagamentoBody = new PagamentoRequest(request.valor());
		PagamentoResponse pagamentoResponse = restClient.post()
				.uri(urlPagamentos + "/api/pagamentos")
				.body(pagamentoBody)
				.retrieve()
				.body(PagamentoResponse.class);

		return new PedidoResponse(
				"SUCESSO",
				"Pedido processado na plataforma Pedidos Veloz.",
				estoqueResponse.mensagem(),
				pagamentoResponse.mensagem()
		);
	}
}

// DTOs locais do próprio fluxo de Pedidos
record PedidoRequest(String produtoId, int quantidade, double valor) {}
record PedidoResponse(String status, String mensagem, String integracaoEstoque, String integracaoPagamento) {}

// Espelhos dos DTOs externos para mapear as respostas das chamadas HTTP
record ReservaRequest(String produtoId, int quantidade) {}
record EstoqueResponse(String status, String mensagem) {}
record PagamentoRequest(double valor) {}
record PagamentoResponse(String status, String mensagem) {}