package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //essa classe se trata de um controlador
@RequestMapping("/postagens")//defina qual ri(postagens) essa classe será acessada
@CrossOrigin("*") //essa classe vai acessar aquisições de qualquer origem
public class PostagemController {

	@Autowired //essa anotação vai o instanciamento - injeta a classe de repositório dentro do nosso controle //criação de interface para aplicação dos métodos
	private PostagemRepository repository;
	
	//métodos 
	//Find All - retorna uma lista do tipo postagem com metodo getall que retorna um objeto com a resposta ok, requerindo todas as postagens
	@GetMapping //se a requisição atraves dessa uri /postagens for feita e for get ela vai ativar esse metodo
	public ResponseEntity<List<Postagem>> findAllPostagem(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	//FindById - - selecionar
	@GetMapping("/{id}") //como parametro o id(de quem está fazendo a requisição) //retorna um único objeto do tipo postagem
	public ResponseEntity<Postagem> findByIdPostagem(@PathVariable /*o valor que entrará na variavel id virá como uma variável do caminho da uri*/ Long id){ 
		return repository.findById(id).map(resp -> ResponseEntity.ok (resp)).orElse(ResponseEntity.notFound().build()); //ele pode vir populado ou nullo, usa-se o map(optional) para devolver uma resposta de ok(não nulo, 200) e se mesmo assim vier diferente do esperado, usamos o else e notFound (404)
	}
	
	//FindByTitulo - selecionar
	@GetMapping("/titulo/{titulo}")//necessáio /titulo(subcaminho com o atributo titulo entre chaves), para não dar duplicidade com o método anterior (id)
	public ResponseEntity<List<Postagem>> findByDescricaoTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.getByTituloContainingIgnoreCase(titulo));
	} //devolve uma lista com tudo que conter o que for digitado (o método que criamos na postagemrepository) // o responseentity.ok(retorna como status 200)
	
	//POST - inserir
	@PostMapping //Post - todos os atributos referente a postagem (nome, titulo data..)
	public ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem){ //@requestbody consegue pegar o que vem do corpo da requisição, espera-se que venha um objeto postagem
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem)); //código para salvar e // o responseentity.status(CREATED)(retorna como status 201)
	}
	
	//PUT - atualizar
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem){ 
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem)); // // o responseentity.ok(retorna como status 200)
	}
	
	//TANTO PARA PUT QUANTO PARA POST USAMOS O MESMO MÉTODO PORÉM NO POSTMAN, NÓS USAMOS O ID PARA O PUT. POIS VAI ATUALIZAR O DADO DE ACORDO COM ID INFORMADO E NO POST NÃO USA O ID, POIS ELE AUTOMATICAMENTE CRIARÁ UM NOVO COM A NOVA INSERÇÃO DE DADOS
	
	//DELETE - 
	@DeleteMapping("/{id}")
	public void /*ou seja, vai retornar vazio*/ deletePostagem(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
