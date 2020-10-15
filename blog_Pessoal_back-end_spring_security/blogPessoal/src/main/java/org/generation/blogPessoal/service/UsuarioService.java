package org.generation.blogPessoal.service;
//essa classe se trata de um serviço

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//implementação da camada de service, ou seja colocar uma regra de negócio, indicando que sempre quando o usuário cadastrar uma senha, essa senha tem que ser inserida dentro do banco de dados já criptografada, ou seja, nem mesmo quem tem acesso ao banco de dados terá acesso a essa senha.

//Outra regra de serviço: é que ele devolva exatamente o token com o prefixo Basic + a senha criptografada assim que o usuário cadastrado logar na aplicação.

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	// injeção da repository
	@Autowired
	private UsuarioRepository repository;

	// REGRA DE NEGOCIO: CADASTRAR (POST - passando pela body, ao invés da path variable (url - não é seguro), pois ela é mais difícil de ser interceptada)
	// método que recebe um usuário (com senha) e retorna um usuário (com senha criptografada): instancia um objeto chamado encoder, com uma variável que vai receber esse objeto, com o método que vai passar a senha do usuário. Chamaremos o usuário, acessando e modificando o atributo senha, e passando essa senha agora criptografada
	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		return repository.save(usuario);
	}

	// REGRA DE NEGOCIO: LOGAR (POST - passando pela body, ao invés da path variable (url - não é seguro), pois ela é mais difícil de ser interceptada)
	// método logar: que retorna um objeto optional e recebe como parâmetro um objeto optional do tipo userLogin, pois vamos retornar para o usuário, as informações: nome, usuário, senha e token cadastradas. Instancia o objeto encoder, cria um objeto do tipo optional usuario que recebe o método para encontrar o usuario digitado pelo cliente (findByUsuario), e acessa o objeto que tem dentro dele, no caso o usuario cadastrado. Se o objeto usuario conter algo, nós iremos comparar a senha criptografada(usuario) com a senha que o usuario digitar(user). Se ambas forem iguais então a regra de negócio será aplicada (devolver a senha criptografada). Iremos mostrar o username e a senha com ":" entre elas, seguindo o padrão byte com Base64 e preenchendo o token (Basic) e o nome, com o que veio no userName. mas caso não entre dentro da condição (venha vazio), retornamos ao usuário um null, pois nãa esxiste esse usuário dentro do nosso banco de dados.
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());
		if (usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte [] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));//utiliza esse padrão, para criptografar o usuario:senha no token facilitando o acesso pelo frontend e dificultando o hackeamento
				String authHeader = "Basic " + new String(encodedAuth);//converte para String o encodedAuth, ou seja, gera uma segunda senha criptografada
				user.get().setToken(authHeader);//preenchendo o token com a senha nova (para liberar o acesso)
				user.get().setNome(usuario.get().getNome());//acessa o objeto optional user através do método set e colocamos o que veio no username, ou seja a senha criptografada
				return user;
			}
		}
		
		return null;//caso não entre nos ifs
	}
	
}

