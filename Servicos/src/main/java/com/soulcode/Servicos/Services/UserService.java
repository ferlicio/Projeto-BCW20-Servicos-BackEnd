package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import com.soulcode.Servicos.Util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtils tokenUtils;

    public List<User> listar() {
        return userRepository.findAll();
    }

    public User cadastrar(User user) {
        return userRepository.save(user);
    }

    public User alterarSenha(String login, String password, String headers){
        if (headers.startsWith("Bearer") && headers != null) {
            String email = tokenUtils.getLogin(headers.substring(7));
            Optional<User> userToken = userRepository.findByLogin(email);
            Optional<User> userParam = userRepository.findByLogin(login);
            if(userToken.get().getLogin() == userParam.get().getLogin()){
//                if (!userParam.get().getPassword().equals(password)){
                userParam.get().setPassword(password);
                return userRepository.save(userParam.get());
            }}

    throw new RuntimeException("ERRO! Tente novamente mais tarde");

    }

    public User desabilitarConta(String login, String headers) {
        if (headers.startsWith("Bearer") && headers != null) {
            String email = tokenUtils.getLogin(headers.substring(7));
            Optional<User> userToken = userRepository.findByLogin(email);

            if (userToken.get().getLogin().equals(login)){
            userToken.get().setEnabled(false);

            return userRepository.save(userToken.get());}

        }
        throw new RuntimeException("ERRO! Tente novamente mais tarde");

    }}
