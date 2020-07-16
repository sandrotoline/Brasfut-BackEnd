package br.gov.sp.fatec.drinkwater.service;

import br.gov.sp.fatec.drinkwater.model.HistoricoConsumo;
import br.gov.sp.fatec.drinkwater.model.Usuario;
import br.gov.sp.fatec.drinkwater.repository.HistoricoConsumoRepository;
import br.gov.sp.fatec.drinkwater.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("historicoConsumoService")
public class HistoricoConsumoServiceImpl implements HistoricoConsumoService {

	@Autowired
	private UsuarioRepository usuarioRepo;

	public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	@Autowired
	private HistoricoConsumoRepository historicoConsumoRepo;

	public void setHistoricoConsumoRepo(HistoricoConsumoRepository historicoConsumoRepo) {
		this.historicoConsumoRepo = historicoConsumoRepo;
	}

	@Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public HistoricoConsumo salvar(HistoricoConsumo historicoConsumo) {
		if(historicoConsumo.getUsuario() != null) {
			Usuario usuario = historicoConsumo.getUsuario();
			if(usuario.getId() != null) {
				usuario = usuarioRepo.findById(usuario.getId()).get();
			}
			else {
				usuario = usuarioRepo.save(usuario);
			}
		}
		historicoConsumo.setDatahora(Calendar.getInstance());
		return historicoConsumoRepo.save(historicoConsumo);
	}

	@Override
	public HistoricoConsumo buscarPorId(Integer id) {
		Optional<HistoricoConsumo> historicoConsumo =  historicoConsumoRepo.findById(id);
		if(historicoConsumo.isPresent()) {
			return historicoConsumo.get();
		}
		return null;
	}

	@Override
	public List<HistoricoConsumo> todos() {
		List<HistoricoConsumo> retorno = new ArrayList<HistoricoConsumo>();
		for(HistoricoConsumo historico: historicoConsumoRepo.findAll()) {
			retorno.add(historico);
		}
		return retorno;
	}

	@Override
	public void excluir(Long id) {
		historicoConsumoRepo.deleteById(Math.toIntExact(id));
	}

	@Override
	@Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public HistoricoConsumo incluirHistoricoConsumo(Long consumoMl, String nomeUsuario, String observacao) {
		Usuario usuario = usuarioRepo.findByNome(nomeUsuario);
		if (usuario == null) {
			return null;
		}

		HistoricoConsumo historicoConsumo = new HistoricoConsumo();
		historicoConsumo.setDatahora(Calendar.getInstance());
		historicoConsumo.setObservacao(observacao);
		historicoConsumo.setConsumoMl(consumoMl);
		historicoConsumo.setUsuario(usuario);
		historicoConsumoRepo.save(historicoConsumo);
		return historicoConsumo;
	}

	@Override
    @PreAuthorize("isAuthenticated()")
	public List<HistoricoConsumo> buscarPorUsuario(String nome) {
		if(nome == null || nome.isEmpty()) {
			return todos();
		}
		return historicoConsumoRepo.findAllByUsuarioNome(nome);
	}

	@Override
    @PreAuthorize("isAuthenticated()")
	public List<HistoricoConsumo> buscaHistoricoConsumoDeUsuarioPorData(Calendar dataInicial, Calendar dataFinal, String nome) {
		return historicoConsumoRepo.findAllByDatahoraBetweenAndUsuarioNome(dataInicial, dataFinal, nome);
	}

	public Long getTotalDeConsumoHoje(String nome) {
	    Calendar horaInicial = Calendar.getInstance();
        Calendar horaFinal = Calendar.getInstance();
	    horaInicial.set(Calendar.HOUR_OF_DAY, 0);
	    horaFinal.set(Calendar.HOUR_OF_DAY, 23);
        List<HistoricoConsumo> historicos = historicoConsumoRepo.findAllByDatahoraBetweenAndUsuarioNome(horaInicial, horaFinal, nome);
        Long totalDeConsumo = 0L;
        for (HistoricoConsumo hist : historicos) {
            totalDeConsumo += hist.getConsumoMl();
        }
        return totalDeConsumo;
    }
}
