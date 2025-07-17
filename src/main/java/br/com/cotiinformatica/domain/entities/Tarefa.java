package br.com.cotiinformatica.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import br.com.cotiinformatica.domain.entities.enums.Prioridades;
import br.com.cotiinformatica.domain.entities.enums.Status;
import lombok.Data;

@Data
@Document(collection = "tarefas")
public class Tarefa {
	
	@Id
	private UUID id;
	
	@Field(name = "nome")
	private String nome;
	
	@Field(name = "dataCriacao")
	private LocalDate dataCriacao;
	
	@Field(name = "dataVencimento")
	private LocalDate dataVencimento;
	
	@Field(name = "descricao")
	private String descricao;
	
	@Field(name = "comentarios")
	private String comentarios;
	
	@Field(name = "status")
	private Status status;
	
	@Field(name = "prioridade")
	private Prioridades prioridade;
	
	@DBRef
	private Projeto projeto;
	
	@Field(name = "usuarioId")
	private UUID usuarioId;
	
	public Tarefa() {
		this.id = UUID.randomUUID();
		this.dataCriacao = LocalDate.now();
	}
}
