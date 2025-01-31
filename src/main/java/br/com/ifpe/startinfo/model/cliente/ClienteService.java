package br.com.ifpe.startinfo.model.cliente;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.startinfo.model.acesso.Perfil;
import br.com.ifpe.startinfo.model.acesso.PerfilRepository;
import br.com.ifpe.startinfo.model.acesso.Usuario;
import br.com.ifpe.startinfo.model.acesso.UsuarioService;
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

    @Transactional
    public Cliente save(Cliente cliente, Usuario usuarioLogado) {

        usuarioService.save(cliente.getUsuario());

            for (Perfil perfil : cliente.getUsuario().getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilUsuarioRepository.save(perfil);
        }

        cliente.setHabilitado(Boolean.TRUE);
        cliente.setCriadoPor(usuarioLogado);
        return repository.save(cliente);

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
        //cliente.setUsername(clienteAlterado.getUsername());
        //cliente.setPassword(clienteAlterado.getPassword());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setCpf(clienteAlterado.getCpf());
        
        cliente.setUltimaModificacaoPor(usuarioLogado);

        repository.save(cliente);

    }

    @Transactional
    public void delete(Long id) {

        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);

        repository.save(cliente);

    }

}