package com.example.backend.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.backend.modelo.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    public Usuario findByUsername(String username);
    List<Usuario> findByEnabledTrue();
    List<Usuario> findByEnabledFalse();

}

