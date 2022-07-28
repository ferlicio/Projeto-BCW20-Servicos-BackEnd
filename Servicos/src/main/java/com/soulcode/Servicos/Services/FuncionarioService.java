package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class FuncionarioService {


    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    @Cacheable("funcionariosCache")
    public List<Funcionario> mostrarTodosFuncionarios(){

        return funcionarioRepository.findAll();
    }


    @Cacheable(value = "funcionariosCache", key = "#idFuncionario")
    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario)
    {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Funcionário não cadastrado: " + idFuncionario)
        );
    }

    @Cacheable(value = "funcionariosCache", key = "#email")
    public Funcionario mostrarUmFuncionarioPeloEmail(String email){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Email do funcionário não cadastrado: " + email)
        );
    }

    @Cacheable(value = "funcionariosCache", key = "#idCargo")
    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargo);
    }

    @CachePut(value = "funcionariosCache", key = "#idCargo")
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo) throws DataIntegrityViolationException {

        funcionario.setIdFuncionario(null);
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        funcionario.setCargo(cargo.get());
        return funcionarioRepository.save(funcionario);
    }

    @CacheEvict(value = "funcionariosCache", key = "#idFuncionario")
    public void excluirFuncionario(Integer idFuncionario){

        funcionarioRepository.deleteById(idFuncionario);
    }

    @CachePut(value = "funcioariosCache")
    public Funcionario editarFuncionario(Funcionario funcionario){

        return funcionarioRepository.save(funcionario);
    }

    @CachePut(value = "funcionariosCache", key = "#caminhoFoto")
    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }
}
