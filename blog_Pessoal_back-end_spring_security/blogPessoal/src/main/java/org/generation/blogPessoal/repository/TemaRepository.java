package org.generation.blogPessoal.repository;

import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long>{
	
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao); 
	//pega tudo que contém aquela palavra digitada e vai ignorar maiúculas e minúsculas e utiliza como parâmetro o atributo descrição

}
