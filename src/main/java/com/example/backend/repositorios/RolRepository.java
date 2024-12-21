package com.example.backend.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.modelo.Rol;



public interface RolRepository extends JpaRepository<Rol,Long> {
}
