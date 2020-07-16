package br.gov.sp.fatec.drinkwater.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.drinkwater.model.Autorizacao;
import br.gov.sp.fatec.drinkwater.model.Usuario;
import br.gov.sp.fatec.drinkwater.repository.AutorizacaoRepository;
import br.gov.sp.fatec.drinkwater.repository.UsuarioRepository;
import br.gov.sp.fatec.drinkwater.security.ChangePassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private AutorizacaoRepository autorizacaoRepo;

	public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	public void setAutorizacaoRepo(AutorizacaoRepository autorizacaoRepo) {
		this.autorizacaoRepo = autorizacaoRepo;
	}

	@Override
  	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public Usuario incluirUsuario(String nome, String senha, String nomeAutorizacao) {
		if (nomeAutorizacao == null) {
			nomeAutorizacao = "ROLE_USER";
		}
		Autorizacao autorizacao = autorizacaoRepo.findByNome(nomeAutorizacao);
		// Se nao existe
		if(autorizacao == null) {
			// Cria nova
			autorizacao = new Autorizacao();
			autorizacao.setNome(nomeAutorizacao);
			autorizacaoRepo.save(autorizacao);
		}
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setSenha(md5(senha));
		usuario.setAutorizacoes(new ArrayList<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		usuarioRepo.save(usuario);
		return usuario;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public Usuario removerUsuario(String nome) {
		Usuario usuario = buscarPorNome(nome);

		if (usuario==null){
			return null;
		}
		usuarioRepo.delete(usuario);
		return usuario;
	}

	@Override
	public Usuario buscarPorNome(String nome) {
		return usuarioRepo.findByNome(nome);
	}
	
	@Override
	@PreAuthorize("isAuthenticated()")
	public Usuario buscarPorId(Long id) {
		Optional<Usuario> usuario =  usuarioRepo.findById(id);
		if(usuario.isPresent()) {
			return usuario.get();
		}
		return null;
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public List<Usuario> todos() {
		List<Usuario> retorno = new ArrayList<Usuario>();
		for(Usuario usuario: usuarioRepo.findAll()) {
			retorno.add(usuario);
		}
		return retorno;
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public Usuario salvar(Usuario usuario) {
		if(!usuario.getAutorizacoes().isEmpty()) {
			for(Autorizacao aut: usuario.getAutorizacoes()) {
				// Se nao existe, cria
				if(aut.getId() == null && autorizacaoRepo.findByNome(aut.getNome()) == null) {
					autorizacaoRepo.save(aut);
				}
			}
		}
		usuario.setSenha(md5(usuario.getSenha()));
		return usuarioRepo.save(usuario);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	public Usuario changePass(ChangePassDTO changePassDTO) {
		Usuario usuario = buscarPorNome(changePassDTO.getUsername());
		if(usuario != null && usuario.getSenha().equals(md5(changePassDTO.getOldPass()))){
			usuario.setSenha(md5(usuario.getSenha()));
			return usuarioRepo.save(usuario);
		}
		return null;
	}

	public Usuario alteraConsumoMl(Long meta, String nome){
		Usuario usuario = buscarPorNome(nome);
		if (usuario == null) {
			return null;
		}
		usuario.setMetadiaria(meta);
		usuarioRepo.save(usuario);
		return usuario;
	}

	private String md5(String senha) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
	
			StringBuilder hexString = new StringBuilder();
			hexString.append("{MD5}");
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02x", 0xFF & b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
			// Unexpected - do nothing
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
			// Unexpected - do nothing			
		}
		return senha;
	}

}
