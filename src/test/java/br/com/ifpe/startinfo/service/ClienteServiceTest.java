package br.com.ifpe.startinfo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.model.acesso.UsuarioService;
import br.com.ifpe.startinfo.model.cliente.Cliente;
import br.com.ifpe.startinfo.model.cliente.ClienteRepository;
import br.com.ifpe.startinfo.model.cliente.ClienteService;
import br.com.ifpe.startinfo.model.mensagens.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UsuarioService usuarioService; // Adicionado Mock de UsuarioService

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private Usuario usuarioLogado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        usuarioLogado = new Usuario();
        usuarioLogado.setUsername("test@example.com");

        cliente = Cliente.builder()
                .nomeCompleto("João da Silva")
                .cpf("123.456.789-00")
                .foneCelular("99999-9999")
                .usuario(usuarioLogado)
                .build();
    }

    @Test
    void deveSalvarClienteComSucesso() {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuarioLogado); // Simula o salvamento do usuário
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.save(cliente, usuarioLogado);

        assertNotNull(clienteSalvo);
        assertEquals("João da Silva", clienteSalvo.getNomeCompleto());
        assertEquals("123.456.789-00", clienteSalvo.getCpf());

        verify(usuarioService, times(1)).save(cliente.getUsuario()); // Verifica se o usuário foi salvo
        verify(clienteRepository, times(1)).save(cliente);
        verify(emailService, times(1)).enviarEmailConfirmacaoCadastroCliente(cliente);
    }
}
