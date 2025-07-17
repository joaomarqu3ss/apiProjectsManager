package br.com.cotiinformatica.domain.interfaces;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.domain.dtos.TarefaRequest;
import br.com.cotiinformatica.domain.dtos.TarefaResponse;

public interface TarefaDomainService {
	
	TarefaResponse criarTarefa(TarefaRequest request, UUID usuarioId);
	
	TarefaResponse atualizarTarefa(UUID id, TarefaRequest request, UUID usuarioId);
	
	TarefaResponse excluir(UUID id, UUID usuarioId);
	
	List<TarefaResponse> consultarTarefas(LocalDate dataMin, LocalDate dataMax, UUID usuarioId);
	
	double calcularMediaTarefasConcluidas(LocalDate dataInicio);
	
}
