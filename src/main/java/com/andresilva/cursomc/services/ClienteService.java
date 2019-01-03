package com.andresilva.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.dto.ClienteDTO;
import com.andresilva.cursomc.repositories.ClienteRepository;
import com.andresilva.cursomc.services.exceptions.DataIntegrityException;
import com.andresilva.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//delcarar propriedade clienteRepository que vais er responsavelpor dar os dados
	@Autowired
	ClienteRepository repository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll() {
		List<Cliente> clientes = repository.findAll();
		Optional<List<Cliente>> optionalCliente = Optional.of(clientes);
		
		return optionalCliente.orElseThrow(() -> new ObjectNotFoundException(    
				"List não encontrada!!!"));
	}

	public Cliente update(Cliente cliente) {
		Cliente newCliente = buscar(cliente.getId());
		updateData(newCliente, cliente);
		
		return repository.save(newCliente);
	}
	
	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas!!");
		}
		
	}
	
	// Métedo para paginar os dados fornecidos (neste caso as clientes)
	// PAGE é uma class do Spring.data
	public Page<Cliente> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
