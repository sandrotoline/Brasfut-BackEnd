package br.gov.sp.fatec.drinkwater.model;

import br.gov.sp.fatec.drinkwater.view.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "HIST_HISTORICO_CONSUMO")
public class HistoricoConsumo implements Serializable {

	private static final long serialVersionUID = -4175224450033765996L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "HIST_ID")
	private Long id;

	@Column(name = "HIST_DATAHORA", nullable = false)
	@JsonView(View.HistoricoResumo.class)
	private Calendar datahora;

	@Column(name = "HIST_CONSUMO_ML", nullable = false)
	@JsonView(View.HistoricoResumo.class)
	private Long consumoMl;

	@Column(name = "HIST_OBSERVACAO", length = 50)
	@JsonView(View.HistoricoCompleto.class)
	private String observacao;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "USR_ID", nullable = false)
	@JsonView(View.HistoricoCompleto.class)
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDatahora() {
		return datahora;
	}

	public void setDatahora(Calendar datahora) {
		this.datahora = datahora;
	}

	public Long getConsumoMl() {
		return consumoMl;
	}

	public void setConsumoMl(Long consumoMl) {
		this.consumoMl = consumoMl;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
