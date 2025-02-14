package br.com.ifpe.startinfo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.model.acesso.UsuarioService;
import br.com.ifpe.startinfo.model.acesso.UsuarioRepository;
import br.com.ifpe.startinfo.model.seguranca.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setUsername("teste@teste.com");
        usuario.setPassword("senha123"); 

        when(passwordEncoder.encode("senha123")).thenReturn("senha123Criptografada");
        usuario.setPassword("senha123Criptografada"); 
    }

   @Test
void deveAutenticarUsuarioComSucesso() {
    when(usuarioRepository.findByUsername("teste@teste.com")).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches("senha123", "senha123Criptografada")).thenReturn(true);

    // Simula a autenticação
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("teste@teste.com", "senha123");
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authenticationToken);

    Usuario usuarioAutenticado = usuarioService.authenticate("teste@teste.com", "senha123");

    assertNotNull(usuarioAutenticado);
    assertEquals("teste@teste.com", usuarioAutenticado.getUsername());

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(usuarioRepository, times(1)).findByUsername("teste@teste.com");
}


   // @Test
    //void deveFalharQuandoUsuarioNaoEncontrado() {
     //   when(usuarioRepository.findByUsername("naoexiste@teste.com")).thenReturn(Optional.empty());

      //  Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
       //     usuarioService.authenticate("naoexiste@teste.com", "senha123");
       // });

       // assertEquals("Usuário não encontrado: naoexiste@teste.com", exception.getMessage());
       // verify(usuarioRepository, times(1)).findByUsername("naoexiste@teste.com");
    //}
}
