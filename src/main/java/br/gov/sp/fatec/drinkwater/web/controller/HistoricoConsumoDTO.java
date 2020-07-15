package br.gov.sp.fatec.drinkwater.web.controller;

public class HistoricoConsumoDTO {

    private Long consumoMl;
    private String observacao;
    private String usuario;

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
