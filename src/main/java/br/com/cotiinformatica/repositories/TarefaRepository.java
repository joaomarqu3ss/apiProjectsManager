package br.com.cotiinformatica.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.dtos.ResultadoMedia;
import br.com.cotiinformatica.domain.entities.Tarefa;
import br.com.cotiinformatica.domain.entities.enums.Status;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, UUID> {
	
	Optional<Tarefa> findByIdAndUsuarioId(UUID id, UUID usuarioId);
	
	List<Tarefa> findByUsuarioIdAndDataCriacaoBetweenOrderByDataCriacaoDesc(UUID usuarioId, LocalDate dataMin, LocalDate dataMax);

	List<Tarefa> findByProjetoId(UUID projetoId);
	
	void deleteByProjeto_Id(UUID projetoId);
	
	int countByProjeto_Id(UUID projetoId);
	
	int countByProjeto_IdAndStatus(UUID projetoId, Status status);
	
	@Aggregation(pipeline = {
	        "{ $match: { status: 'CONCLUIDA', dataVencimento: { $gte: ?0 } } }",
	        "{ $group: { _id: '$usuarioId', total: { $sum: 1 } } }",
	        "{ $group: { _id: null, media: { $avg: '$total' } } }"
	    })
	    List<ResultadoMedia> calcularMediaTarefasConcluidas(LocalDate dataInicio);

}
