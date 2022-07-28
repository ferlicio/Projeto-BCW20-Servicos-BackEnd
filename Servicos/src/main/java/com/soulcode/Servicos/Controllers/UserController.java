package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import com.soulcode.Servicos.Security.AuthorizationFilter;
import com.soulcode.Servicos.Services.UserService;
import com.soulcode.Servicos.Util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("servicos")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public List<User> usuarios() {
        return userService.listar();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> inserirUsuario(@RequestBody User user) {
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/usuarios/{loginUsuario}")
    public ResponseEntity<User> alterarSenha(@PathVariable String loginUsuario, @RequestParam("password") String password, @RequestHeader ("Authorization") String headers){
        String senhaUser = passwordEncoder.encode(password);
        userService.alterarSenha(loginUsuario, senhaUser, headers);
        return ResponseEntity.ok().build();
    }


    @PutMapping("desabilitaContas/{loginUsuario}")
    public ResponseEntity<User> desabilitarConta(@PathVariable String loginUsuario, @RequestHeader  ("Authorization") String headers){
        userService.desabilitarConta(loginUsuario, headers);
        return ResponseEntity.ok().build();
    }
}



