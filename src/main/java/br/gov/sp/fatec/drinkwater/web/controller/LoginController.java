package br.gov.sp.fatec.drinkwater.web.controller;

import javax.servlet.http.HttpServletResponse;

import br.gov.sp.fatec.drinkwater.model.Usuario;
import br.gov.sp.fatec.drinkwater.security.JwtUtils;
import br.gov.sp.fatec.drinkwater.security.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class LoginController {
    
    @Autowired
    private AuthenticationManager auth;
    
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @CrossOrigin(exposedHeaders = "Token")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws JsonProcessingException {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Authentication credentials = new UsernamePasswordAuthenticationToken(username, password);
        Usuario usuario = (Usuario) auth.authenticate(credentials).getPrincipal();
        response.setHeader("Token", JwtUtils.generateToken(usuario));
        loginDTO.setPassword(null);
        loginDTO.setToken(JwtUtils.generateToken(usuario));
        loginDTO.setAutorizacoes(usuario.getAutorizacoes());
        return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
    }


}
