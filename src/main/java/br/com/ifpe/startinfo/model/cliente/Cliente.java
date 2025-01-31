package br.com.ifpe.startinfo.model.cliente;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;

//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "Cliente")
@SQLRestriction("habilitado = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel  {

   @OneToOne
   @JoinColumn(nullable = false)
   private Usuario usuario;

   //@OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
   //@Fetch(FetchMode.SUBSELECT)
   //private List<EnderecoCliente> enderecos;

   @Column(name = "nomeCompleto", nullable = false, length = 100)
   private String nomeCompleto;
  
   @Column(name = "celular", nullable = false)
   private String foneCelular;

   @Column(name = "cpf", nullable = false, unique = true)
   private String cpf;

}

