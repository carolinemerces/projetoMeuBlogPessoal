package org.generation.blogPessoal.repository;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
//jparepository<nome da model, tipo do id>
	//permite fazer algumas manipulações dentro do banco de dados como inserir, atualizar, deletar e selecionar (tudo ou algo em específico)

	public List<Postagem> getByTituloContainingIgnoreCase(String titulo);// titulo = atributo da entidade
	//na lista postagem ache tudo que esteja no titulo como (like) maiusculo ou minusculo - fará a padronização
	
}
