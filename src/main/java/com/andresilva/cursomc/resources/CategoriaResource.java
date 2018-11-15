package com.andresilva.cursomc.resources;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	//GET
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Categoria categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	//POST
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
		categoria = service.insert(categoria);
		
		/*
		 * BOA PRÁTICA REST: após ser feita a inseerção num banco dados deve ser dada uma resposta com codigo de 
		 * sucesso (201) e dar o URI do objecto adicionado
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	//PUT
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id) {
		categoria.setId(id);
		categoria = service.update(categoria);
		
		return ResponseEntity.noContent().build();
	}
}
