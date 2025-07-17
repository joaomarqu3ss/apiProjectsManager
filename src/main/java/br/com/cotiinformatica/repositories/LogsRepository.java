package br.com.cotiinformatica.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.cotiinformatica.domain.entities.Log;

public interface LogsRepository extends MongoRepository<Log, UUID> {

}
