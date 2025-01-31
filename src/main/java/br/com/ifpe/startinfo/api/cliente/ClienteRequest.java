package br.com.ifpe.startinfo.api.cliente;

import java.util.Arrays;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.startinfo.model.acesso.Perfil;
import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.model.cliente.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
  
   @NotBlank(message = "O e-mail é de preenchimento obrigatório!")
   @Email(message = "O e-mail fornecido é inválido!")
   private String username;

   @NotBlank(message = "A senha é de preenchimento obrigatório!")
   private String password;

   @NotBlank(message = "O nome completo é de preenchimento obrigatório!")
   @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
   private String nomeCompleto;

   @NotBlank(message = "O telefone celular é de preenchimento obrigatório!")
   @Length(min = 15, max = 15, message = "O campo Fone tem que ter entre {min} e {max} caracteres")
   private String foneCelular;

   @NotBlank(message = "O CPF é de preenchimento obrigatório!")
   @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato XXX.XXX.XXX-XX")
   private String cpf;

   public Cliente build() {
        return Cliente.builder()
           .usuario(buildUsuario())
           .nomeCompleto(nomeCompleto)
           .foneCelular(foneCelular)
           .cpf(cpf)
           .build();
    }

    public Usuario buildUsuario() {
        return Usuario.builder()
           .username(username)
           .password(password)
           .roles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)))
           .build();
    }

}