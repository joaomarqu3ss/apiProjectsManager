package br.com.cotiinformatica.domain.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ProjetoComTarefasResponse(
		UUID id, String nome, LocalDate dataCriacao, int quantidadeTarefas , int quantidadeTarefasConcluidas
		) {

}
