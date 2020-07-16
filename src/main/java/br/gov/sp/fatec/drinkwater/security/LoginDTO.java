package br.gov.sp.fatec.drinkwater.security;

import br.gov.sp.fatec.drinkwater.model.Autorizacao;

import java.util.List;

public class LoginDTO {
	
	private String username;
	private String password;
	private List<Autorizacao> autorizacoes;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;


	public LoginDTO() {
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

}
