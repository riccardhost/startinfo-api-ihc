package br.com.ifpe.startinfo.model.cliente;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.startinfo.model.acesso.Perfil;
import br.com.ifpe.startinfo.model.acesso.PerfilRepository;
import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.model.acesso.UsuarioService;
import br.com.ifpe.startinfo.model.mensagens.EmailService;
import br.com.ifpe.startinfo.util.exception.EntidadeNaoEncontradaException;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;
    
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Cliente save(Cliente cliente, Usuario usuarioLogado) {

        usuarioService.save(cliente.getUsuario());

            for (Perfil perfil : cliente.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);
        }

        cliente.setHabilitado(Boolean.TRUE);
        cliente.setCriadoPor(usuarioLogado);
        Cliente clienteSalvo = repository.save(cliente);

        emailService.enviarEmailConfirmacaoCadastroCliente(clienteSalvo);

        return clienteSalvo;

    }

    public List<Cliente> listarTodos() {

    return repository.findAll();

    }

    public Cliente obterPorID(Long id) {

        Optional<Cliente> consulta = repository.findById(id);
    
        if (consulta.isPresent()) {
            return consulta.get();
        } else {
            throw new EntidadeNaoEncontradaException("Cliente", id);
        }

    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado, Usuario usuarioLogado) {
    
        Cliente cliente = repository.findById(id).get();
        cliente.setNomeCompleto(clienteAlterado.getNomeCompleto());
        cliente.setBiografia(clienteAlterado.getBiografia());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());

        cliente.setUltimaModificacaoPor(usuarioLogado);

        repository.save(cliente);

    }

    @Transactional
    public void delete(Long id) {

        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);

        repository.save(cliente);
    }

    @Transactional
    public EnderecoCliente adicionarEnderecoCliente(Long clienteId, EnderecoCliente endereco) {

        Cliente cliente = this.obterPorID(clienteId);
        
        //Primeiro salva o EnderecoCliente:
        endereco.setCliente(cliente);
        endereco.setHabilitado(Boolean.TRUE);
        enderecoClienteRepository.save(endereco);
        
        //Depois acrescenta o endereço criado ao cliente e atualiza o cliente:
        List<EnderecoCliente> listaEnderecoCliente = cliente.getEnderecos();
        
        if (listaEnderecoCliente == null) {
            listaEnderecoCliente = new ArrayList<EnderecoCliente>();
        }
        
        listaEnderecoCliente.add(endereco);
        cliente.setEnderecos(listaEnderecoCliente);
        repository.save(cliente);
        
        return endereco;
    }

    @Transactional
    public EnderecoCliente atualizarEnderecoCliente(Long id, EnderecoCliente enderecoAlterado) {

        EnderecoCliente endereco = enderecoClienteRepository.findById(id).get();
        endereco.setCep(enderecoAlterado.getCep());
        endereco.setRua(enderecoAlterado.getRua());
        endereco.setNumero(enderecoAlterado.getNumero());
        endereco.setBairro(enderecoAlterado.getBairro());
        endereco.setCidade(enderecoAlterado.getCidade());
        endereco.setEstado(enderecoAlterado.getEstado());
        endereco.setReferencia(enderecoAlterado.getReferencia());

        return enderecoClienteRepository.save(endereco);
    }

    @Transactional
    public void removerEnderecoCliente(Long idEndereco) {

        EnderecoCliente endereco = enderecoClienteRepository.findById(idEndereco).get();
        endereco.setHabilitado(Boolean.FALSE);
        enderecoClienteRepository.save(endereco);

        Cliente cliente = this.obterPorID(endereco.getCliente().getId());
        cliente.getEnderecos().remove(endereco);
        repository.save(cliente);
    }

}

