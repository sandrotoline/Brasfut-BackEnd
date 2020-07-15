package br.gov.sp.fatec.drinkwater.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.gov.sp.fatec.drinkwater.model.Autorizacao;

public interface AutorizacaoRepository extends CrudRepository<Autorizacao, Long> {
	
	public Autorizacao findByNome(String nome);
	
	public List<Autorizacao> findByNomeContainsIgnoreCase(String nome);

}
