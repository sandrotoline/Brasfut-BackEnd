package br.gov.sp.fatec.drinkwater.service;

import java.util.Collection;
import java.util.List;

import br.gov.sp.fatec.drinkwater.model.Usuario;

public interface UsuarioService {
	
	public Usuario incluirUsuario(String nome, String senha, String nomeAutorizacao);
	
	public Usuario buscarPorNome(String nome);
	
	public Usuario buscarPorId(Long id);
	
	public List<Usuario> todos();
	
	public Usuario salvar(Usuario usuario);

}
