package br.gov.sp.fatec.drinkwater.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.gov.sp.fatec.drinkwater.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Usuario findByNome(String nome);

	public Usuario findTop1ByNomeContains(String nome);
	
	public List<Usuario> findByIdGreaterThan(Long id);

	public List<Usuario> findByAutorizacoesNomeContainsIgnoreCase(String nome);
	
	public List<Usuario> findByNomeContainsIgnoreCaseOrAutorizacoesNomeContainsIgnoreCase(String nomeUsuario, String nomeAutorizacao);
}
