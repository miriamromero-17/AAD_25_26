package com.example.add.repository;

import com.example.add.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

    Optional<Module> findByCode(String code);

}
