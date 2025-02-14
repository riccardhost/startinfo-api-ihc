package br.com.ifpe.startinfo.service;

import static org.junit.jupiter.api.Assertions.*;
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
import java.util.Optional;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setUsername("test@example.com");
        usuario.setPassword("senha123");
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        when(usuarioRepository.findByUsername("test@example.com")).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn("fake-jwt-token");

        Usuario usuarioAutenticado = usuarioService.authenticate("test@example.com", "senha123");

        assertNotNull(usuarioAutenticado);
        assertEquals("test@example.com", usuarioAutenticado.getUsername());

        verify(usuarioRepository, times(1)).findByUsername("test@example.com");
        verify(jwtService, times(1)).generateToken(usuario);
    }

    @Test
    void deveFalharQuandoUsuarioNaoEncontrado() {
        when(usuarioRepository.findByUsername("naoexiste@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.authenticate("naoexiste@example.com", "senha123");
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername("naoexiste@example.com");
    }
}
