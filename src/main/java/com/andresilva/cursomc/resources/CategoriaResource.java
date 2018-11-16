package com.andresilva.cursomc.resources;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.dto.CategoriaDTO;
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
	
	
	//GET All Categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Categoria> categorias = service.findAll();
		
		// o metedo .collect(Collectors.toList()); serve para converte/fazer cast novamente para o list
		List<CategoriaDTO> categoriaDTOs = categorias.stream().map(ob -> new CategoriaDTO(ob)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriaDTOs);
	}
		
	
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Métedo para paginar os dados fornecidos (neste caso as categorias)
	/*
	 * neste uri vai ser construido como uma query string Ex: http://localhost/categorias?page=10&linesPerPage=1....etc
	 * para isso usamos a anotação @RequestParam(value = "linesPerPage", defaultValue = "24") o valor é o nome da variavel 
	 * para a a query string e o defaultValue é para que os paremetros possam ser OPTIONAL 
	 * NOTA: no linesPage usamos os defaultValue é 24, porque 24 é multiplo de 1, 2, 3 e 4 .... e assim é mais facil 
	 * organizar os dados de forma responsiva numa pagina conforme o tamanho....1 em 1, 2 em 2, 3 em 3
	 */
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) 
	{
		
		Page<Categoria> categorias = service.findPage(page, linesPage, orderBy, direction);
		
		// o metedo .collect(Collectors.toList()); serve para converte/fazer cast novamente para o list
		//List<CategoriaDTO> categoriaDTOs = categorias.stream().map(ob -> new CategoriaDTO(ob)).collect(Collectors.toList());
		Page<CategoriaDTO> categoriaDTOs = categorias.map(ob -> new CategoriaDTO(ob));
		return ResponseEntity.ok().body(categoriaDTOs);
	}
}
