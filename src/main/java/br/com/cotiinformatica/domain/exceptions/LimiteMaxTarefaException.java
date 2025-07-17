package br.com.cotiinformatica.domain.exceptions;

@SuppressWarnings("serial")
public class LimiteMaxTarefaException extends RuntimeException {
	
	@Override
	public String getMessage() {
			return "Limite máximo de tarefas atingido para este projeto.";
	}
}
