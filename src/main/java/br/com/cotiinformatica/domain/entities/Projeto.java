package br.com.cotiinformatica.domain.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "projetos")
public class Projeto {
	
	@Id
	private UUID id;
	
	@Field(name = "nome")
	private String nome;
	
	@DBRef(lazy = false)
	private List<Tarefa> tarefas;
	
	@Field(name = "data_criacao")
	private LocalDate dataCriacao;
	
	@Field(name = "usuario_id")
	private UUID usuarioId;
	
	public Projeto() {
		this.id = UUID.randomUUID();
		this.dataCriacao = LocalDate.now();
	}
	
}
