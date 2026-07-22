package com.faculdade.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class EstoqueApplication {
	public static void main(String[] args) {
		SpringApplication.run(EstoqueApplication.class, args);
	}
}

@RestController
@RequestMapping("/api/estoque")
class EstoqueController {

	@PostMapping("/reserva")
	public EstoqueResponse reservarItens(@RequestBody ReservaRequest request) {
		// Simulação de baixa no estoque
		return new EstoqueResponse("RESERVADO", "Produto ID " + request.produtoId() + " reservado com sucesso (Quantidade: " + request.quantidade() + ").");
	}
}

record ReservaRequest(String produtoId, int quantidade) {}
record EstoqueResponse(String status, String mensagem) {}