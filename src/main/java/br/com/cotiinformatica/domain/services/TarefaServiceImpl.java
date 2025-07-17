package br.com.cotiinformatica.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.dtos.TarefaRequest;
import br.com.cotiinformatica.domain.dtos.TarefaResponse;
import br.com.cotiinformatica.domain.entities.Log;
import br.com.cotiinformatica.domain.entities.Tarefa;
import br.com.cotiinformatica.domain.entities.enums.Prioridades;
import br.com.cotiinformatica.domain.entities.enums.Status;
import br.com.cotiinformatica.domain.exceptions.LimiteMaxTarefaException;
import br.com.cotiinformatica.domain.exceptions.ProjetoNotFoundException;
import br.com.cotiinformatica.domain.exceptions.TarefaNotFoundException;
import br.com.cotiinformatica.domain.interfaces.TarefaDomainService;
import br.com.cotiinformatica.repositories.LogsRepository;
import br.com.cotiinformatica.repositories.ProjetoRepository;
import br.com.cotiinformatica.repositories.TarefaRepository;

@Service
public class TarefaServiceImpl implements TarefaDomainService {

	@Autowired TarefaRepository tarefaRepository;
	@Autowired ProjetoRepository projetoRepository;
	@Autowired LogsRepository logsRepository;
		
	@Override
	public TarefaResponse criarTarefa(TarefaRequest request, UUID usuarioId) {
		var projeto = projetoRepository.findByIdAndUsuarioId(request.projetoId(), usuarioId)
			    .orElseThrow(() -> new ProjetoNotFoundException());

			if (projeto.getTarefas() != null && projeto.getTarefas().size() >= 20) {
			    throw new LimiteMaxTarefaException();
			}

			var tarefa = new Tarefa();
			tarefa.setNome(request.nome());
			tarefa.setComentarios(request.comentarios());
			tarefa.setDescricao(request.descricao());
			tarefa.setStatus(Status.valueOf(request.status()));
			tarefa.setPrioridade(Prioridades.valueOf(request.prioridade()));
			tarefa.setDataVencimento(LocalDate.parse(request.dataVencimento()));
			tarefa.setProjeto(projeto);
			tarefa.setUsuarioId(usuarioId);

			tarefaRepository.save(tarefa);
			
			var log = new Log();
			log.setId(tarefa.getId());
			log.setNome(tarefa.getNome());
			log.setDataCriacao(tarefa.getDataCriacao());
			log.setDataVencimento(tarefa.getDataVencimento());
			log.setDescricao(tarefa.getDescricao());
			log.setComentarios(tarefa.getComentarios());
			log.setStatus(tarefa.getStatus());
			log.setPrioridade(tarefa.getPrioridade());
			log.setProjeto(tarefa.getProjeto());
			log.setUsuarioId(tarefa.getUsuarioId());
			
			logsRepository.save(log);
		
						
			return copyToResponse(tarefa);	
	}

	@Override
	public TarefaResponse atualizarTarefa(UUID id, TarefaRequest request, UUID usuarioId) {
		var projeto = projetoRepository.findByIdAndUsuarioId(request.projetoId(), usuarioId)
				.orElseThrow(() -> new ProjetoNotFoundException());
		
		var tarefa = tarefaRepository.findByIdAndUsuarioId(id, usuarioId)
				.orElseThrow(() -> new TarefaNotFoundException());
		
		tarefa.setNome(request.nome());
		tarefa.setDescricao(request.descricao());
		tarefa.setComentarios(request.comentarios());
		tarefa.setStatus(Status.valueOf(request.status()));
		tarefa.setDataVencimento(LocalDate.parse(request.dataVencimento()));
		tarefa.setProjeto(projeto);
		tarefa.setUsuarioId(usuarioId);
		
		tarefaRepository.save(tarefa);
		
		var log = new Log();
		log.setId(tarefa.getId());
		log.setNome(tarefa.getNome());
		log.setDataCriacao(tarefa.getDataCriacao());
		log.setDataVencimento(tarefa.getDataVencimento());
		log.setDescricao(tarefa.getDescricao());
		log.setComentarios(tarefa.getComentarios());
		log.setStatus(tarefa.getStatus());
		log.setPrioridade(tarefa.getPrioridade());
		log.setProjeto(tarefa.getProjeto());
		log.setUsuarioId(tarefa.getUsuarioId());
		
		logsRepository.save(log);
		
		return copyToResponse(tarefa);
		
	}

	@Override
	public TarefaResponse excluir(UUID id, UUID usuarioId) {
		
		var tarefa = tarefaRepository.findByIdAndUsuarioId(id, usuarioId)
				.orElseThrow(() -> new TarefaNotFoundException());
		
		tarefaRepository.delete(tarefa);
		
		return copyToResponse(tarefa);
	}

	@Override
	public List<TarefaResponse> consultarTarefas(LocalDate dataMin, LocalDate dataMax, UUID usuarioId) {
		
		var tarefas = tarefaRepository.findByUsuarioIdAndDataCriacaoBetweenOrderByDataCriacaoDesc(usuarioId, dataMin, dataMax);	
		if(tarefas.isEmpty()) {
			throw new TarefaNotFoundException();
		}
		return tarefas.stream()
				.map(tarefa -> copyToResponse(tarefa))
				.toList();
	}
	public TarefaResponse copyToResponse(Tarefa tarefa) {
		return new TarefaResponse(
				tarefa.getId(), 
				tarefa.getNome(), 
				tarefa.getDataCriacao(), 
				tarefa.getDataVencimento().toString(), tarefa.getDescricao(),
				tarefa.getComentarios(), 
				tarefa.getStatus().toString(), 
				tarefa.getPrioridade().toString(),
				tarefa.getProjeto().getId()
			);								
	}
	@Override
	public double calcularMediaTarefasConcluidas(LocalDate dataInicio) {
		dataInicio = LocalDate.now().minusDays(30);
		var resultado = tarefaRepository.calcularMediaTarefasConcluidas(dataInicio);
		if (resultado.isEmpty()) {
			return 0.0;
		}
		return resultado.get(0).media();
	}
}
