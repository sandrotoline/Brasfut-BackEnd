package br.gov.sp.fatec.drinkwater.web.controller;

import javax.servlet.http.HttpServletResponse;

import br.gov.sp.fatec.drinkwater.model.Usuario;
import br.gov.sp.fatec.drinkwater.security.JwtUtils;
import br.gov.sp.fatec.drinkwater.security.Login;
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
    public ResponseEntity<Login> login(@RequestBody Login login, HttpServletResponse response) throws JsonProcessingException {
        String username = login.getUsername();
        String password = login.getPassword();
        Authentication credentials = new UsernamePasswordAuthenticationToken(username, password);
        Usuario usuario = (Usuario) auth.authenticate(credentials).getPrincipal();
        response.setHeader("Token", JwtUtils.generateToken(usuario));
        login.setPassword(null);
        login.setToken(JwtUtils.generateToken(usuario));
        return new ResponseEntity<Login>(login, HttpStatus.OK);
    }


}
