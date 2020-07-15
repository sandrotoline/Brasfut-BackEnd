package br.gov.sp.fatec.drinkwater.web.controller;

import java.util.Collection;
import java.util.List;

import br.gov.sp.fatec.drinkwater.model.HistoricoConsumo;
import br.gov.sp.fatec.drinkwater.service.HistoricoConsumoService;
import br.gov.sp.fatec.drinkwater.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/historicoConsumo")
@CrossOrigin
public class HistoricoConsumoController {

	@Autowired
	HistoricoConsumoService historicoConsumoService;

	public void setHistoricoConsumoService(HistoricoConsumoService historicoConsumoService) {
		this.historicoConsumoService = historicoConsumoService;
	}

	@GetMapping("/getById/{id}")
	//	@JsonView(View.Anotacao.class)
	public ResponseEntity<HistoricoConsumo> buscaPorId(@PathVariable(value="id") Integer id) {
		HistoricoConsumo historicoConsumo = historicoConsumoService.buscarPorId(id);
		if(historicoConsumo == null) {
			return new ResponseEntity<HistoricoConsumo>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HistoricoConsumo>(historicoConsumo, HttpStatus.OK);
	}

	@GetMapping("/getByUser/{nome}")
	//	@JsonView(View.Anotacao.class)
	public ResponseEntity<Collection<HistoricoConsumo>> buscaPorNome(@PathVariable(value="nome") String nome) {
		List<HistoricoConsumo> historicoConsumo = historicoConsumoService.buscarPorUsuario(nome);
		if(historicoConsumo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<HistoricoConsumo>>(historicoConsumo, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	@ResponseBody
//	@JsonView(View.Anotacao.class)
	public ResponseEntity<Collection<HistoricoConsumo>> getAll() {
		return new ResponseEntity<Collection<HistoricoConsumo>>(historicoConsumoService.todos(), HttpStatus.OK);
	}

//	@RequestMapping(value = "/save", method = RequestMethod.POST)
////	@JsonView(View.Anotacao.class)
//	public ResponseEntity<Anotacao> salvar(@RequestBody Anotacao anotacao, UriComponentsBuilder uriComponentsBuilder) {
//		anotacao = anotacaoService.salvar(anotacao);
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.setLocation(uriComponentsBuilder.path("/getById/" + anotacao.getId()).build().toUri());
//		return new ResponseEntity<Anotacao>(anotacao, responseHeaders, HttpStatus.CREATED);
//	}

	@PostMapping("/addHistorico")
//	@JsonView(View.HistoricoResumo.class)
	public ResponseEntity<HistoricoConsumo> addHistoricoConsumo(@RequestBody HistoricoConsumoDTO historicoConsumo, HttpServletRequest request, HttpServletResponse response) {
		HistoricoConsumo historicoConsumo1 = historicoConsumoService.incluirHistoricoConsumo(historicoConsumo.getConsumoMl(),
				historicoConsumo.getUsuario(), historicoConsumo.getObservacao());
		response.addHeader("Location", request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/usuario/getById?id=" + historicoConsumo1.getId());
		return new ResponseEntity<HistoricoConsumo>(historicoConsumo1, HttpStatus.CREATED);
	}
	
}
