package br.com.cotiinformatica.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.JwtTokenComponent;
import br.com.cotiinformatica.domain.dtos.TarefaRequest;
import br.com.cotiinformatica.domain.dtos.TarefaResponse;
import br.com.cotiinformatica.domain.interfaces.TarefaDomainService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tarefas")
public class TarefaController {
	
	@Autowired TarefaDomainService tarefaDomainService;
	@Autowired JwtTokenComponent jwtTokenComponent;
	
	@PostMapping
	public ResponseEntity<TarefaResponse> criarTarefa(@RequestBody @Valid TarefaRequest request, HttpServletRequest http) {
		var response = tarefaDomainService.criarTarefa(request, getUsuarioId(http));
		
		return ResponseEntity.ok(response);
	}
	@PutMapping("{id}")
	public ResponseEntity<TarefaResponse> atualizarTarefa(@PathVariable UUID id, @RequestBody @Valid TarefaRequest request,
			HttpServletRequest http) {
		
		var response = tarefaDomainService.atualizarTarefa(id, request, getUsuarioId(http));
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<TarefaResponse> excluirTarefa(@PathVariable UUID id, HttpServletRequest http) {
		
		var response = tarefaDomainService.excluir(id, getUsuarioId(http));
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{dataMin}/{dataMax}")
	public ResponseEntity<List<TarefaResponse>> getAll(@PathVariable String dataMin, @PathVariable String dataMax, HttpServletRequest http){
		
		
		var response = tarefaDomainService.consultarTarefas(LocalDate.parse(dataMin), LocalDate.parse(dataMax), getUsuarioId(http));
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("media-concluidas")
	public ResponseEntity<Double> getMediaConcluidas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			HttpServletRequest http){
		
		var perfil = getUsuarioPerfil(http);
		if(!"Gerente".equalsIgnoreCase(perfil)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		
		var response = tarefaDomainService.calcularMediaTarefasConcluidas(dataInicio);
				
		return ResponseEntity.ok(response);
	}
	
	public UUID getUsuarioId(HttpServletRequest http) {
		var token = http.getHeader("Authorization")
				.replace("Bearer" , "");
		
		return jwtTokenComponent.getIdFromToken(token);
	}
	private String getUsuarioPerfil(HttpServletRequest http) {
	    var token = http.getHeader("Authorization").replace("Bearer ", "");
	    return jwtTokenComponent.getPerfilFromToken(token);
	
	}

}
