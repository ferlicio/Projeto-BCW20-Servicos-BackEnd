package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado,Integer> {

    List<Chamado> findByCliente(Optional<Cliente> cliente);

    List<Chamado> findByFuncionario(Optional<Funcionario> funcionario);

    @Query(value = "SELECT * FROM chamado WHERE status =:status",nativeQuery = true )
    List<Chamado> findByStatus(String status);

    @Query(value="SELECT * FROM chamado WHERE data_entrada BETWEEN :data1 AND :data2", nativeQuery = true)
    List<Chamado> findByIntervaloData(Date data1, Date data2);

    //Card 29
    @Query(value="SELECT chamado.*, pagamento.status_pagamento\n" +
            "FROM chamado\n" +
            "LEFT JOIN pagamento ON chamado.id_pagamento = pagamento.id_pagamento\n" +
            "WHERE pagamento.status_pagamento = \"LANCADO\"", nativeQuery = true)
    List<Chamado> chamadosLancados();

    //Card 30
    @Query(value="SELECT chamado.*, pagamento.status_pagamento\n" +
            "FROM chamado\n" +
            "LEFT JOIN pagamento ON chamado.id_pagamento = pagamento.id_pagamento\n" +
            "WHERE pagamento.status_pagamento = \"QUITADO\"", nativeQuery = true)
    List<Chamado> chamadosQuitados();

    //Card 26
    @Query(value = "SELECT COUNT(status) as QUANTIDADE, chamado.status as STATUS_CHAMADO FROM chamado GROUP BY chamado.status", nativeQuery = true)
    List<Object> listaChamadoPorStatus ();

}
