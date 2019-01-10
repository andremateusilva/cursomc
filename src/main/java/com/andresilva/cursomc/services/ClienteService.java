package com.andresilva.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.domain.Cidade;
import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.domain.Endereco;
import com.andresilva.cursomc.domain.enums.TipoCliente;
import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.dto.ClienteDTO;
import com.andresilva.cursomc.dto.ClienteNewDTO;
import com.andresilva.cursomc.repositories.ClienteRepository;
import com.andresilva.cursomc.repositories.EnderecoRepository;
import com.andresilva.cursomc.services.exceptions.DataIntegrityException;
import com.andresilva.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//delcarar propriedade clienteRepository que vais er responsavelpor dar os dados
	@Autowired
	ClienteRepository repository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		
		return cliente;
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
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!!");
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

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()));
		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if (clienteNewDTO.getTelefone2() != null ) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		
		if (clienteNewDTO.getTelefone3() != null ) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		return cliente;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
