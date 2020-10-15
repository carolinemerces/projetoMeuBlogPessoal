package org.generation.blogPessoal.security;
//essa classe contém a configuração de segurança

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // habita a configuração de websecurity e deve herdar de outra classe
					// WebSecurityConfigurerAdapter da mesma biblioteca
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	// Faz uma "injeção" de dependência dentro da repository e nesse caso de uma
	// classe que já existe dentro da classe WebSecurityConfigurerAdapter
	@Autowired
	private UserDetailsService userDetailsService;

	// método configure = anotação vai sobescrever o método que tem dentro de userDetailsService (configure)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception/* tratativa de erros */ { //
		auth.userDetailsService(userDetailsService); // assim que esse método for chamado, o objeto auth (coocado como
														// parâmetro) vai ser chamado com o método injetado
	}

	// Anotação Bean = criptografae uma senha, deixando esse método disponível para todas as classes e caso tenha algum problema ele roda até encontrar e rodas na bean
	// método para password que não recebe nada como parâmetro
	@Bean
	public PasswordEncoder passWordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// outro método sobescrito chamado configure do tipo void
	@Override
	protected void configure(HttpSecurity http) throws Exception { //assim que esse método for inicado ele vai instanciar um HttpSecurity com um objeto http 
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()/*serve para liberar endpoints, ou seja, alguns caminhos dentro do meu controller para que o cliente consiga fazer requisições dentro da api e ter acesso sem precisar utilizar uma chave token*/
		.antMatchers("/usuarios/cadastrar").permitAll()
		.anyRequest()/*as demais requisições*/.authenticated()/*deverão ser autenticadas, vai ter que passar a chave no header*/
		.and().httpBasic()/*vai utilizar o padrão basic para gerar a chave token*/
		.and().sessionManagement()/*indica qual é o tipo de sessão que vamos utilizar*/.sessionCreationPolicy(SessionCreationPolicy.STATELESS)/*criação de uma política de sessão que não guarda sessão nenhuma*/
		.and().cors()/*comando para habilitar o cors dentro da camada security*/
		.and().csrf().disable();/*para desabilitar as configurações do csrf, para utilizar todas as configurações padrões*/
	}

}
