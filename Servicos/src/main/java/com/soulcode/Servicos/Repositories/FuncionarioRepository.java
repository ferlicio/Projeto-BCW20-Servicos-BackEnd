package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);
    
    List<Funcionario> findByCargo(Optional<Cargo> cargo);

    @Query(value = "SELECT * FROM funcionario WHERE foto is null",nativeQuery = true )
    List<Object> funcionarioSemFoto();

    @Query(value = "SELECT funcionario.*, chamado.id_chamado FROM funcionario LEFT JOIN chamado ON funcionario" +
            ".id_funcionario = chamado.id_funcionario WHERE chamado.id_funcionario is null", nativeQuery = true)
    List<Object> funcionarioSemChamado();

    @Query(value = "SELECT COUNT(funcionario.id_cargo) as QUANTIDADE, cargo.nome\n" +
            "FROM funcionario \n" +
            "LEFT JOIN cargo\n" +
            "\tON funcionario.id_cargo = cargo.id_cargo\n" +
            "GROUP BY funcionario.id_cargo", nativeQuery = true)
    List<Object> qtdFuncionarioPeloCargo();

}
