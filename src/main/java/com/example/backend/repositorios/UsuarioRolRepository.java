package com.example.backend.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.backend.modelo.UsuarioRol;


public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
	//List<UsuarioRol> findByRolRolNombre(String rolNombre);
	List<UsuarioRol> findByRolRolNombreAndUsuarioEnabled(String rolNombre, boolean enabled);
}
