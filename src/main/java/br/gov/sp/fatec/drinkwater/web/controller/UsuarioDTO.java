package br.gov.sp.fatec.drinkwater.web.controller;

public class UsuarioDTO {

    private String nome;
    private String senha;
    private String autorizacao;
    private Long metadiaria;
    private String token;

    public Long getMetadiaria() {
        return metadiaria;
    }

    public void setMetadiaria(Long metadiaria) {
        this.metadiaria = metadiaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
