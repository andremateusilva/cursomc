package com.andresilva.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClientesResource {
	
	// 1 - Criar camada service (que vai comunicar com repository)
	// 2 - declarar a propriedade service
	@Autowired
	ClienteService clienteService;
	
	// Métedos de acesso à camada service para obter os dados de service (que posteriormente comunica com o repository)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		Cliente cliente = clienteService.buscar(id);
		
		return ResponseEntity.ok().body(cliente);
	}

}
