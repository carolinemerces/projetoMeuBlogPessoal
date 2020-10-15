package org.generation.blogPessoal.security;
//esta classe trata de um serviço (encontrar o usuário pelo userName)
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	//injeção da repository
	@Autowired
	private UsuarioRepository userRepository;
	
	//método sobescrito de UserDetailsServive, onde o método loadUserByUsername vai encontrar um usuário pelo userName e vai retornar um novo UserDetails extraindo o que existe dentro do objeto, caso a condição seja verdadeira se não retornará não encontrado 
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Usuario> user = userRepository.findByUsuario(userName);
		user.orElseThrow(() -> new UsernameNotFoundException(userName + "not found.")); //caso dê um erro chamamos essa tratativa
	return user.map(UserDetailsImpl::new).get(); //retorna um novo UserDetails extraindo o que existe dentro do objeto 
	}
}
