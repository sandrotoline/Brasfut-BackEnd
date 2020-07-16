package br.gov.sp.fatec.drinkwater.service;

import br.gov.sp.fatec.drinkwater.model.HistoricoConsumo;

import java.util.Calendar;
import java.util.List;

public interface HistoricoConsumoService {

	public HistoricoConsumo salvar(HistoricoConsumo historicoConsumo);

	public HistoricoConsumo buscarPorId(Integer id);

	public List<HistoricoConsumo> todos();

	public void excluir(Long id);

	public HistoricoConsumo incluirHistoricoConsumo(Long consumoMl, String nomeUsuario, String observacao);

	public List<HistoricoConsumo> buscarPorUsuario(String nome);

	public List<HistoricoConsumo> buscaHistoricoConsumoDeUsuarioPorData(Calendar dataInicial, Calendar dataFinal, String nome);

    public Long getTotalDeConsumoHoje(String nome);

}
