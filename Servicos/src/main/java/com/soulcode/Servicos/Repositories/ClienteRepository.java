package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository  extends JpaRepository<Cliente,Integer> {

    @Query(value="SELECT chamado.id_cliente, SUM(pagamento.valor) as TOTAL\n" +
            "            FROM chamado\n" +
            "            LEFT JOIN pagamento\n" +
            "            ON chamado.id_pagamento = pagamento.id_pagamento\n" +
            "            WHERE pagamento.status_pagamento = \"QUITADO\"\n" +
            "            GROUP BY chamado.id_cliente", nativeQuery = true)
    List<Object> totalPagoPorClientePorChamadoConcluido();

}
