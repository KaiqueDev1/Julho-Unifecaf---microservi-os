package com.faculdade.pagamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class PagamentoApplication {
	public static void main(String[] args) {
		SpringApplication.run(PagamentoApplication.class, args);
	}
}

@RestController
@RequestMapping("/api/pagamentos")
class PagamentosController {

	@PostMapping
	public PagamentoResponse processarPagamento(@RequestBody PagamentoRequest request) {
		// Simulação de sucesso no pagamento
		return new PagamentoResponse("PAGO", "Transação autorizada com sucesso para o valor: R$ " + request.valor());
	}
}

record PagamentoRequest(double valor) {}
record PagamentoResponse(String status, String mensagem) {}