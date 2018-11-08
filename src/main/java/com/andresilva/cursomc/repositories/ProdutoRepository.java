package com.andresilva.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.andresilva.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
}