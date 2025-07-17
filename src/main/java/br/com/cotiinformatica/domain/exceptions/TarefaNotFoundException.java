package br.com.cotiinformatica.domain.exceptions;

@SuppressWarnings("serial")
public class TarefaNotFoundException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Tarefa não encontrada.";
	}
}
