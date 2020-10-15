package org.generation.blogPessoal.controller;
//construção da camada controller, onde serão feitos 2 métodos: Autenticação e Cadastrar. Esses métodos irão passar pelo filtro de segurança, mas serão liberados (na classe BasicSecurityConfig)

import java.util.Optional;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Indica que essa classe se trata de uma controller
@RequestMapping ("/usuarios") //indica o caminho
@CrossOrigin(origins = "*", allowedHeaders = "*")//aceita qualquer origem e informação
public class UsuarioController {

	@Autowired //injeção da classe de serviços (UsuarioService), no lugar da classe repository (UsuarioRepository)
	private UsuarioService usuarioService;
	
	//AUTENTICATION - LOGAR
	//método POST - passando pela body, ao invés da path variable (url - não é seguro), pois ela é mais difícil de ser interceptada. O usuário não pode ter acesso ao login e a senha que esteja sendo passada. O Método Autentication através da classe userLogin (somente para resposta), pela body,  vai receber alguma coisa do tipo userLogin com o nome do objeto user. Ele loga e retorna o token ao usuário
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user){
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	//CADASTRAR
	//método POST - vai receber como parâmetro via body um objeto do tipo usuário (que vai para a base de dados), chamando o método feito no repository (CadastrarUsuario), retornando para o usuário, chamando o serviço (UsuarioService - regra de negócio), a mensagem CREATED 201.
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.CadastrarUsuario(usuario));
	}
	
	
}
