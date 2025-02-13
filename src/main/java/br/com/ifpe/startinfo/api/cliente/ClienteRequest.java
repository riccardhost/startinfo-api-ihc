package br.com.ifpe.startinfo.api.cliente;

import java.time.LocalDate;
import java.util.Arrays;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.startinfo.model.acesso.Perfil;
import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.model.cliente.Cliente;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
   private String email;

   @NotBlank(message = "A senha é de preenchimento obrigatório!")
   private String password;

   @NotBlank(message = "O nome completo é de preenchimento obrigatório!")
   @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
   private String nomeCompleto;

   @Length(max = 230, message = "A biografia deverá ter no máximo {max} caracteres")
   private String biografia;

   @NotBlank(message = "O telefone celular é de preenchimento obrigatório!")
   @Length(min = 15, max = 15, message = "O campo Fone tem que ter entre {min} e {max} caracteres")
   private String foneCelular;

   @NotBlank(message = "O CPF é de preenchimento obrigatório!")
   private String cpf;

   @JsonFormat(pattern = "dd/MM/yyyy")
   private LocalDate dataNascimento;

   @Lob
   private byte[] photo;

   public Cliente build() {
        return Cliente.builder()
           .usuario(buildUsuario())
           .nomeCompleto(nomeCompleto)
           .biografia(biografia)
           .foneCelular(foneCelular)
           .cpf(cpf)
           .dataNascimento(dataNascimento)
           .photo(photo)
           .build();
    }

    public Usuario buildUsuario() {
        return Usuario.builder()
           .username(email)
           .password(password)
           .roles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)))
           .build();
    }

}