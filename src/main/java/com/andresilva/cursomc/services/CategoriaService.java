package com.andresilva.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		
		return categoria.orElse(null);
	}
}
