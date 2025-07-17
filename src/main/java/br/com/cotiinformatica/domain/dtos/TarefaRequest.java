package br.com.cotiinformatica.domain.dtos;

import java.util.UUID;

public record TarefaRequest(
		String nome, String descricao ,String comentarios, String status, String prioridade, String dataVencimento, UUID projetoId
		) {

}
