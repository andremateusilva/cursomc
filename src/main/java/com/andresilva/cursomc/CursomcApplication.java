package com.andresilva.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.domain.Produto;
import com.andresilva.cursomc.repositories.CategoriaRepository;
import com.andresilva.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Aqui estamos a criar objectos do tipo de Categoria
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		// Aqui estamos a criar objectos do tipo de Produto
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		// Aqui estamos associar/criar a relação entre tabelas
		// Estamos associar o(s) produto(s) as categorias correspondentes 
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		// Estamos associar a(s) categoria(s) as produtos correspondentes
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		// Aqui estamos a salvar/popular a base dados com as tabelas correspondentes por EX: tabela categorias, produtos e a tabela entermédia (visto haver uma relação de muitos para muitos) 
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}
}
