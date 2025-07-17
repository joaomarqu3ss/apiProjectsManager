package br.com.cotiinformatica.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.cotiinformatica.domain.exceptions.LimiteMaxTarefaException;
import br.com.cotiinformatica.domain.exceptions.ProjetoNotFoundException;
import br.com.cotiinformatica.domain.exceptions.TarefaNotFoundException;
import br.com.cotiinformatica.domain.exceptions.TarefaPendenteException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ProjetoNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleContaNaoEncontradaException(ProjetoNotFoundException ex, WebRequest request){
		var body = new HashMap<String, Object>();
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TarefaPendenteException.class)
	public ResponseEntity<Map<String, Object>> handleTarefaPendenteException(TarefaPendenteException ex, WebRequest request){
		var body = new HashMap<String, Object>();
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LimiteMaxTarefaException.class)
	public ResponseEntity<Map<String, Object>> handleTarefaPendenteException(LimiteMaxTarefaException ex, WebRequest request){
		var body = new HashMap<String, Object>();
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(TarefaNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleTarefaPendenteException(TarefaNotFoundException ex, WebRequest request){
		var body = new HashMap<String, Object>();
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
