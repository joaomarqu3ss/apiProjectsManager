package br.com.cotiinformatica.domain.exceptions;

@SuppressWarnings("serial")
public class ProjetoNotFoundException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "Projeto não encontrado. Verifique o ID informado.";
	}
}
