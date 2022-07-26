package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento,Integer> {

    @Query(value = "SELECT * FROM pagamento WHERE pagamento.status_pagamento = :status", nativeQuery = true)
    List<Pagamento> findByStatus(String status);


    @Query(value="SELECT pagamento.*, chamado.id_chamado,chamado.titulo, cliente.id_cliente,cliente.nome\n" +
            "\tFROM chamado RIGHT JOIN pagamento ON chamado.id_chamado = pagamento.id_pagamento\n" +
            "    LEFT JOIN cliente ON cliente.id_cliente = chamado.id_cliente",nativeQuery = true)
    List<List> orcamentoComServicoCliente();

    //Card 25
    @Query(value = "SELECT COUNT(status_pagamento) as QUANTIDADE, pagamento.status_pagamento as STATUS_PAGAMENTO FROM pagamento GROUP BY pagamento.status_pagamento", nativeQuery = true)
    List<Object> listaPagamentoPorStatus();

}
