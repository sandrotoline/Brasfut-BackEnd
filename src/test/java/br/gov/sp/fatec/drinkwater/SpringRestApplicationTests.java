package br.gov.sp.fatec.drinkwater;

import static org.junit.jupiter.api.Assertions.*;

import br.gov.sp.fatec.drinkwater.model.Usuario;
import br.gov.sp.fatec.drinkwater.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class SpringRestApplicationTests {

	private static final String NOME = "Usuário X";
	private static final String SENHA = "X";
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	/**
	 * @param usuarioRepo the usuarioRepo to set
	 */
	public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	@Test
	public void testeInsercaoOk() {
		Usuario usuario = new Usuario();
		usuario.setNome(NOME);
		usuario.setSenha(SENHA);
		usuarioRepo.save(usuario);
		assertTrue(usuario.getId() != null);
	}

}
