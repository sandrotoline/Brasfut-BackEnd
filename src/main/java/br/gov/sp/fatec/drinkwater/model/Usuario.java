package br.gov.sp.fatec.drinkwater.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USR_USUARIO")
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1507218635788384719L;

	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "USR_ID")
//	@JsonView({View.UsuarioCompleto.class, View.UsuarioResumoAlternativo.class, View.Anotacao.class})
	private Long id;
    
    @Column(name = "USR_NOME", unique=true, length = 20, nullable = false)
//    @JsonView({View.UsuarioResumo.class, View.UsuarioResumoAlternativo.class, View.Anotacao.class})
    private String nome;
    
    @Column(name = "USR_SENHA", length = 50, nullable = false)
//    @JsonView(View.UsuarioCompleto.class)
    private String senha;

	@Column(name = "USR_META_DIARIA")
	private Long metadiaria;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UAU_USUARIO_AUTORIZACAO", 
    	joinColumns = { @JoinColumn(name = "USR_ID") }, 
    	inverseJoinColumns = { @JoinColumn(name = "AUT_ID") })
//    @JsonView({View.UsuarioResumo.class, View.UsuarioResumoAlternativo.class})
    private List<Autorizacao> autorizacoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autorizacoes;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return senha;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return nome;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

}
