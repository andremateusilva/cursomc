package com.andresilva.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.domain.Pedido;
import com.andresilva.cursomc.repositories.PedidoRepository;
import com.andresilva.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
			
		return pedido.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
	
	}

}

