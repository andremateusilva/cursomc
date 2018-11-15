package com.andresilva.cursomc.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.repositories.CategoriaRepository;
import com.andresilva.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		
		//return categoria.orElse(null);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria insert(Categoria categoria) {
		/*
		 * usamos o setId a null para garantir que o id(a nova categoria) ainda não existe, pois caso já exista
		 *  o metedo save() iria considerar que era uma actualização e actualiza em vez de inserir...
		 */
		categoria.setId(null);
		return repository.save(categoria);
	}
}
