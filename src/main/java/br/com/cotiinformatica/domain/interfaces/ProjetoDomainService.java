package br.com.cotiinformatica.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.domain.dtos.ProjetoComTarefasResponse;
import br.com.cotiinformatica.domain.dtos.ProjetoRequest;
import br.com.cotiinformatica.domain.dtos.ProjetoResponse;

public interface ProjetoDomainService {
	
	ProjetoResponse criarProjeto(ProjetoRequest request, UUID usuarioId);
	
	ProjetoResponse atualizarProjeto(UUID id, ProjetoRequest request, UUID usuarioId);
	
	ProjetoResponse excluir(UUID id, UUID usuarioId);
	
	List<ProjetoComTarefasResponse> consultarProjetos(UUID usuarioId);
	
	ProjetoResponse obterProjetoPorId(UUID id, UUID usuarioId);
	
}
