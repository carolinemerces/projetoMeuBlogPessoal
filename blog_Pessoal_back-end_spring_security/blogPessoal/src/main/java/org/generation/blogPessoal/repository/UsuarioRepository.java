package org.generation.blogPessoal.repository;
//essa interface constrói toda comunicação com a base de dados
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

//herda da classe usuário (annotations e atributos)
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	//método find by usuario pelo username
	public Optional<Usuario> findByUsuario(String usuario /*atributo*/);
	

}
