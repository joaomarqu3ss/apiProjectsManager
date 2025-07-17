package br.com.cotiinformatica.domain.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ProjetoResponse(
			UUID id, String nome, LocalDate dataCriacao
		) {

}
