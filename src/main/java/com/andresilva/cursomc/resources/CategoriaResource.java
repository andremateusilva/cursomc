package com.andresilva.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // na boa prática o rest coloca-se o verbo do end point no plural
public class CategoriaResource {
	
	// na boa pratica do rest deve-se identificar correctamente os verbos http para cada operação que queremos neste caso o Get 
//	@RequestMapping(method=RequestMethod.GET)
//	public List<Categoria> listar() {
//		Categoria cat1 = new Categoria(1, "Informática");
//		Categoria cat2 = new Categoria(2, "Escritório");
//		
//		List<Categoria> categorias = new ArrayList<>();
//		categorias.add(cat1);
//		categorias.add(cat2);
//		
//		return categorias;
//	}
	
	@Autowired
	CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {

		Categoria categoria = service.buscar(id);
		return ResponseEntity.ok().body(categoria);
	}
}
