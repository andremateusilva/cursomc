package com.andresilva.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.dto.CategoriaDTO;
import com.andresilva.cursomc.dto.ClienteDTO;
import com.andresilva.cursomc.dto.ClienteNewDTO;
import com.andresilva.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClientesResource {
	
	// 1 - Criar camada service (que vai comunicar com repository)
	// 2 - declarar a propriedade service
	@Autowired
	ClienteService service;
	
	// Métedos de acesso à camada service para obter os dados de service (que posteriormente comunica com o repository)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Cliente cliente = service.buscar(id);
		
		return ResponseEntity.ok().body(cliente);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) { 
		//QUANDO USAMOS ASSERTIONS E ASSIM TEMOS QUE USAR O @Valid
		Cliente cliente = service.fromDTO(clienteNewDTO);
		cliente = service.insert(cliente);
		
		/*
		 * BOA PRÁTICA REST: após ser feita a inseerção num banco dados deve ser dada uma resposta com codigo de 
		 * sucesso (201) e dar o URI do objecto adicionado
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	//GET All Clientes
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Cliente> categorias = service.findAll();
		
		// o metedo .collect(Collectors.toList()); serve para converte/fazer cast novamente para o list
		List<ClienteDTO> categoriaDTOs = categorias.stream().map(ob -> new ClienteDTO(ob)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriaDTOs);
	}
		
	//PUT
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO categoriaDTO, @PathVariable Integer id) {
		Cliente categoria = service.fromDTO(categoriaDTO);
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
	public ResponseEntity<Page<ClienteDTO>> findPage (
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) 
	{
		
		Page<Cliente> categorias = service.findPage(page, linesPage, orderBy, direction);
		
		// o metedo .collect(Collectors.toList()); serve para converte/fazer cast novamente para o list
		//List<ClienteDTO> categoriaDTOs = categorias.stream().map(ob -> new ClienteDTO(ob)).collect(Collectors.toList());
		Page<ClienteDTO> categoriaDTOs = categorias.map(ob -> new ClienteDTO(ob));
		return ResponseEntity.ok().body(categoriaDTOs);
	}
}
