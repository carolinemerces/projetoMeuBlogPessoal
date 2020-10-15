package org.generation.blogPessoal.security;
//esta classe cria os atibutos userName e senha e os métodos para autorizar o acesso ao banco de dados com eles depois
import java.util.Collection;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	
	//classe apenas para contole interno
	private static final long serialVersionUID = 1L;
	
	//atributos
	private String userName;
	private String password;
	
	
	//construtor de classe para os atributos, populando esse user com login e senha do Ususario model
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}

	//construtor vazio
	public UserDetailsImpl() {}
	
	
	//todos os métodos da implementação UserDetails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	//Senha
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	//Usuário
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
		

}


