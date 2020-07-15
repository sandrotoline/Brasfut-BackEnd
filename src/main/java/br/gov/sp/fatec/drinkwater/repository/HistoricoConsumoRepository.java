package br.gov.sp.fatec.drinkwater.repository;

import java.util.Calendar;
import java.util.List;

import br.gov.sp.fatec.drinkwater.model.HistoricoConsumo;
import org.springframework.data.repository.CrudRepository;

public interface HistoricoConsumoRepository extends CrudRepository<HistoricoConsumo, Integer> {

	//    @PreAuthorize("isAuthenticated()")
	public List<HistoricoConsumo> findAllByUsuarioNome(String nome);

	//    @PreAuthorize("isAuthenticated()")
	public List<HistoricoConsumo> findAllByDatahoraBetweenAndUsuarioNome(Calendar dataInicial, Calendar dataFinal, String nome);
}


