package br.com.cotiinformatica.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.dtos.ProjetoComTarefasResponse;
import br.com.cotiinformatica.domain.dtos.ProjetoRequest;
import br.com.cotiinformatica.domain.dtos.ProjetoResponse;
import br.com.cotiinformatica.domain.entities.Projeto;
import br.com.cotiinformatica.domain.entities.enums.Status;
import br.com.cotiinformatica.domain.exceptions.ProjetoNotFoundException;
import br.com.cotiinformatica.domain.exceptions.TarefaPendenteException;
import br.com.cotiinformatica.domain.interfaces.ProjetoDomainService;
import br.com.cotiinformatica.repositories.ProjetoRepository;
import br.com.cotiinformatica.repositories.TarefaRepository;

@Service
public class ProjetoServiceImpl implements ProjetoDomainService {

	@Autowired ProjetoRepository projetoRepository;
	@Autowired TarefaRepository tarefaRepository;
	
	@Override
	public ProjetoResponse criarProjeto(ProjetoRequest request, UUID usuarioId) {
		
		var projeto = new Projeto();
		
		projeto.setNome(request.nome());
		projeto.setUsuarioId(usuarioId);
		
		projetoRepository.save(projeto);
		
		return copyToResponse(projeto);		
	}

	@Override
	public ProjetoResponse atualizarProjeto(UUID id, ProjetoRequest request, UUID usuarioId) {
		var projeto = projetoRepository.findByIdAndUsuarioId(id, usuarioId)
				.orElseThrow(() -> new ProjetoNotFoundException());
		
		projeto.setNome(request.nome());
		projetoRepository.save(projeto);
		
		return copyToResponse(projeto);
		
	}

	@Override
	public ProjetoResponse excluir(UUID id, UUID usuarioId) {
		
		var projeto = projetoRepository.findByIdAndUsuarioId(id, usuarioId)
				.orElseThrow(() -> new ProjetoNotFoundException());
		
		var tarefas = tarefaRepository.findByProjetoId(projeto.getId());
		
		boolean todasConcluidas = tarefas == null || tarefas.isEmpty()
				|| tarefas.stream().allMatch(t -> t.getStatus() == Status.CONCLUIDA);
		
		if(todasConcluidas) {
			tarefaRepository.deleteByProjeto_Id(projeto.getId());
			
			projetoRepository.delete(projeto);
		} else {
			throw new TarefaPendenteException();
		}
		return copyToResponse(projeto);
	}

	@Override
	public List<ProjetoComTarefasResponse> consultarProjetos(UUID usuarioId) {
		var projetos = projetoRepository.findByUsuarioId(usuarioId);
		
		return projetos.stream()
				.map(projeto -> {
					int qtdTarefas = tarefaRepository.countByProjeto_Id(projeto.getId());
					int qtdConcluidas = tarefaRepository.countByProjeto_IdAndStatus(projeto.getId(), Status.CONCLUIDA);
					var response = new ProjetoComTarefasResponse(
							projeto.getId(),
							projeto.getNome(),
							projeto.getDataCriacao(),
							qtdTarefas,
							qtdConcluidas);
					return response;
				}).toList();
	}

	@Override
	public ProjetoResponse obterProjetoPorId(UUID id, UUID usuarioId) {
		
		var projeto = projetoRepository.findByIdAndUsuarioId(id, usuarioId)
				.orElseThrow(() -> new ProjetoNotFoundException());
		
		return copyToResponse(projeto);	
	}
	
	
	
	private ProjetoResponse copyToResponse(Projeto projeto) {
		return new ProjetoResponse(
				projeto.getId(),
				projeto.getNome(),
				projeto.getDataCriacao());
				
	}

}
