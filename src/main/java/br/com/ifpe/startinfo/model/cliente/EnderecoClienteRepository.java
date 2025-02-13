package br.com.ifpe.startinfo.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Long> {
    
}
