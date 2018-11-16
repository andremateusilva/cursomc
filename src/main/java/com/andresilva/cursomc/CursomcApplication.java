package com.andresilva.cursomc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andresilva.cursomc.domain.Categoria;
import com.andresilva.cursomc.domain.Cidade;
import com.andresilva.cursomc.domain.Cliente;
import com.andresilva.cursomc.domain.Endereco;
import com.andresilva.cursomc.domain.Estado;
import com.andresilva.cursomc.domain.ItemPedido;
import com.andresilva.cursomc.domain.Pagamento;
import com.andresilva.cursomc.domain.PagamentoComBoleto;
import com.andresilva.cursomc.domain.PagamentoComCartao;
import com.andresilva.cursomc.domain.Pedido;
import com.andresilva.cursomc.domain.Produto;
import com.andresilva.cursomc.domain.enums.EstadoPagamento;
import com.andresilva.cursomc.domain.enums.TipoCliente;
import com.andresilva.cursomc.repositories.CategoriaRepository;
import com.andresilva.cursomc.repositories.CidadeRepository;
import com.andresilva.cursomc.repositories.ClienteRepository;
import com.andresilva.cursomc.repositories.EnderecoRepository;
import com.andresilva.cursomc.repositories.EstadoRepository;
import com.andresilva.cursomc.repositories.ItemPedidoRepository;
import com.andresilva.cursomc.repositories.PagamentoRepository;
import com.andresilva.cursomc.repositories.PedidoRepository;
import com.andresilva.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Aqui estamos a criar objectos do tipo de Categoria
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "casa mesa e banho");
		Categoria cat4 = new Categoria(null, "Fotografia");
		Categoria cat5 = new Categoria(null, "Livros de BD");
		Categoria cat6 = new Categoria(null, "Video jogos");
		Categoria cat7 = new Categoria(null, "Música");
		
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
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		// Aqui estamos a salvar/popular a base dados com as tabelas correspondentes por EX: tabela categorias, produtos e a tabela entermédia (visto haver uma relação de muitos para muitos) 
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco endereco1 = new Endereco(null, "Rua FLores", "300", "Apto 303", "Jardim", "38220834", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);

		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, simpleDateFormat.parse("30/09/17 12:26"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("10/10/17 14:01"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, simpleDateFormat.parse("20/10/17 00:01"), null);
		pedido1.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
//		cliente1.setPedidos(Arrays.asList(pedido1, pedido2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		
		ItemPedido ip1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedido2, p2, 100.00, 1, 800.00);
		
		pedido1.getItems().addAll(Arrays.asList(ip1, ip2));
		pedido2.getItems().addAll(Arrays.asList(ip3));
		
		p1.getItems().addAll(Arrays.asList(ip1));
		p2.getItems().addAll(Arrays.asList(ip3));
		p3.getItems().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
}
