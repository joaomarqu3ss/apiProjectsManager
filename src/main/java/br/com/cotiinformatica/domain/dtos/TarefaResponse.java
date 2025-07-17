package br.com.cotiinformatica.domain.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record TarefaResponse(
			UUID id, String nome, 
			LocalDate dataCriacao, String dataVencimento, String descricao,
			String comentarios, String status, 
			String prioridade, UUID projetoId
		) {

}
