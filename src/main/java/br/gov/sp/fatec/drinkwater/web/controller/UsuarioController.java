package br.gov.sp.fatec.drinkwater.web.controller;

import java.util.Collection;
import java.util.List;

import br.gov.sp.fatec.drinkwater.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.drinkwater.service.UsuarioService;
import br.gov.sp.fatec.drinkwater.view.View;

import com.fasterxml.jackson.annotation.JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@RequestMapping(value = "/get/{nome}", method = RequestMethod.GET)
//	@JsonView(View.UsuarioCompleto.class)
	public ResponseEntity<Usuario> pesquisar(@PathVariable("nome") String nome) {
		return new ResponseEntity<Usuario>(usuarioService.buscarPorNome(nome), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
//	@JsonView(View.UsuarioCompleto.class)
	public ResponseEntity<Usuario> get(@RequestParam(value="id", defaultValue="1") Long id) {
		Usuario usuario = usuarioService.buscarPorId(id);
		if(usuario == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
//	@JsonView(View.UsuarioResumoAlternativo.class)
	public ResponseEntity<Collection<Usuario>> getAll() {
		List<Usuario> todos = usuarioService.todos();
		return new ResponseEntity<Collection<Usuario>>(todos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	@JsonView(View.UsuarioCompleto.class)
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario, UriComponentsBuilder uriComponentsBuilder) {
		usuario = usuarioService.salvar(usuario);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(uriComponentsBuilder.path("/getById?id=" + usuario.getId()).build().toUri());
		return new ResponseEntity<Usuario>(usuario, responseHeaders, HttpStatus.CREATED);
	}

	@PostMapping("/addUser")
	public ResponseEntity<UsuarioDTO> addUsuario(@RequestBody UsuarioDTO usuario, UriComponentsBuilder uriComponentsBuilder) {
		usuarioService.incluirUsuario(usuario.getNome(), usuario.getSenha(), usuario.getAutorizacao());
//        response.addHeader("Location", request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/usuario/getById?id=" + usuario.getId());
		return new ResponseEntity<UsuarioDTO>(usuario, HttpStatus.CREATED);
	}

	@PostMapping("/setMetaDiaria")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario, UriComponentsBuilder uriComponentsBuilder) {
		Usuario usuario1 = usuarioService.alteraConsumoMl(usuario.getMetadiaria(), usuario.getNome());
		if (usuario1 != null) {
			return new ResponseEntity<Usuario>(usuario1, HttpStatus.CREATED);
		}
		return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
	}
}
