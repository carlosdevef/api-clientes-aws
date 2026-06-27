package br.com.cotiinformatica.api_clientes.entities;

import br.com.cotiinformatica.api_clientes.enums.StatusCliente;
import br.com.cotiinformatica.api_clientes.enums.TipoCliente;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cliente {

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private StatusCliente status;
    private TipoCliente tipo;

}
