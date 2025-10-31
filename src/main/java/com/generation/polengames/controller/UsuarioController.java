package com.generation.polengames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.polengames.model.Usuario;
import com.generation.polengames.model.UsuarioLogin;
import com.generation.polengames.repository.UsuarioRepository;
import com.generation.polengames.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarios.forEach( usuario -> usuarioService.calculaIdade(usuario));
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
		
	@GetMapping("/{id}")  
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return usuarioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> getfindAllByNomeContainingIgnoreCase(@PathVariable String nome) {
		return ResponseEntity.ok(usuarioRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<List<Usuario>> getAllByNome(@PathVariable String email) {
		return ResponseEntity.ok(usuarioRepository.findAllByEmailContainingIgnoreCase(email));
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario) {
		return usuarioService.cadastrarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario) {
		return usuarioService.atualizarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> autenticar(@Valid @RequestBody Optional<UsuarioLogin> usuarioLogin) {
		return usuarioService.autenticarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping("/{id}")
//	public void delete(@PathVariable Long id) {
//		
//		Optional<Usuario> usuario = usuarioRepository.findById(id);
//		
//		if(usuario.isEmpty())
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//		
//		usuarioRepository.deleteById(id);
//	}
	
}
