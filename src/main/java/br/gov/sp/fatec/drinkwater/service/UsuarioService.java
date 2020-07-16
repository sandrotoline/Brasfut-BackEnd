package br.gov.sp.fatec.drinkwater.service;

import java.util.List;

import br.gov.sp.fatec.drinkwater.model.Usuario;
import br.gov.sp.fatec.drinkwater.security.ChangePassDTO;

public interface UsuarioService {
	
	public Usuario incluirUsuario(String nome, String senha, String nomeAutorizacao);

	public Usuario removerUsuario(String nome);

	public Usuario buscarPorNome(String nome);
	
	public Usuario buscarPorId(Long id);
	
	public List<Usuario> todos();
	
	public Usuario salvar(Usuario usuario);

	public Usuario changePass(ChangePassDTO changePassDTO);

	public Usuario alteraConsumoMl(Long meta, String nome);
}
