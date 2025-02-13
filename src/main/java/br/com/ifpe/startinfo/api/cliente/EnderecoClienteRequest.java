package br.com.ifpe.startinfo.api.cliente;

import lombok.Data;
import lombok.Builder;
import br.com.ifpe.startinfo.model.cliente.EnderecoCliente;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoClienteRequest {

    private String cep;

    private String rua;
 
    private String numero;
 
    private String bairro;
   
    private String cidade;
 
    private String estado;
 
    private String referencia;
 
    public EnderecoCliente build() {
 
        return EnderecoCliente.builder()
                .cep(cep)
                .rua(rua)
                .numero(numero)
                .bairro(bairro)
                .cidade(cidade)
                .estado(estado)
                .referencia(referencia)
                .build();
    }
 }
