package br.com.cotiinformatica.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.entities.Projeto;

@Repository
public interface ProjetoRepository extends MongoRepository<Projeto, UUID> {

	Optional<Projeto> findByUsuarioId(UUID usuarioId);
	
	Optional<Projeto> findByIdAndUsuarioId(UUID id, UUID usuarioId);
	
	
	
}
