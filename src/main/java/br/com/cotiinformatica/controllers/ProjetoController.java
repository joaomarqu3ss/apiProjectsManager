package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.JwtTokenComponent;
import br.com.cotiinformatica.domain.dtos.ProjetoComTarefasResponse;
import br.com.cotiinformatica.domain.dtos.ProjetoRequest;
import br.com.cotiinformatica.domain.dtos.ProjetoResponse;
import br.com.cotiinformatica.domain.interfaces.ProjetoDomainService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projetos")
public class ProjetoController {
	
	@Autowired ProjetoDomainService projetoDomainService;
	@Autowired JwtTokenComponent jwtTokenComponent;
	
	@PostMapping
	public ResponseEntity<ProjetoResponse> criarProjeto(@RequestBody @Valid ProjetoRequest request, HttpServletRequest http) {
		
		var response = projetoDomainService.criarProjeto(request, getUsuarioId(http));
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ProjetoResponse> atualizarProjeto(@PathVariable UUID id, @RequestBody @Valid ProjetoRequest request,
			HttpServletRequest http){
		
		var response = projetoDomainService.atualizarProjeto(id, request, getUsuarioId(http));
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ProjetoResponse> excluirProjeto(@PathVariable UUID id, HttpServletRequest http){
		
		var response = projetoDomainService.excluir(id,getUsuarioId(http));
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<ProjetoComTarefasResponse>> listarProjetos(HttpServletRequest http) {
		
		var response = projetoDomainService.consultarProjetos(getUsuarioId(http));
		
		return ResponseEntity.ok(response);
}
	@GetMapping("{id}")
	public ResponseEntity<ProjetoResponse> consultarProjeto(@PathVariable UUID id, HttpServletRequest http){
		
		var response = projetoDomainService.obterProjetoPorId(id, getUsuarioId(http));
		
		return ResponseEntity.ok(response);
		
	}
	
	private UUID getUsuarioId(HttpServletRequest http) {
		
		var token = http.getHeader("Authorization")
				.replace("Bearer ", "");
		
		return jwtTokenComponent.getIdFromToken(token);
	}
	
}
