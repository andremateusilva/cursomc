package com.andresilva.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.repositories.ClienteRepository;
import com.andresilva.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//delcarar propriedade clienteRepository que vais er responsavelpor dar os dados
	@Autowired
	ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

}
