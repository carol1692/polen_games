package com.generation.polengames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.polengames.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public List <Usuario> findAllByNomeContainingIgnoreCase(String nome);
	
	public List<Usuario> findAllByEmailContainingIgnoreCase(String email);
	
	Optional<Usuario> findByEmail(String email);
}
