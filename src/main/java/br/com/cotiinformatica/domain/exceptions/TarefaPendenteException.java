package br.com.cotiinformatica.domain.exceptions;

@SuppressWarnings("serial")
public class TarefaPendenteException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Existem tarefas pendentes para o projeto. Não é possível excluí-lo.";
	}
}
