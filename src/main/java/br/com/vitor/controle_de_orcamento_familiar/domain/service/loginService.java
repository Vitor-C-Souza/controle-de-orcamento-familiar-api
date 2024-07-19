package br.com.vitor.controle_de_orcamento_familiar.domain.service;

import br.com.vitor.controle_de_orcamento_familiar.domain.dto.LoginDtoRequest;
import br.com.vitor.controle_de_orcamento_familiar.domain.dto.LoginDtoResponse;
import br.com.vitor.controle_de_orcamento_familiar.domain.model.Usuario;
import br.com.vitor.controle_de_orcamento_familiar.domain.repository.usuarioRepository;
import br.com.vitor.controle_de_orcamento_familiar.infra.exception.usuarioDuplicadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class loginService implements UserDetailsService {
    @Autowired
    private usuarioRepository repository;

    public LoginDtoResponse cadastro(LoginDtoRequest dto, String senhaEncriptada) {
        Usuario novoUsuario = new Usuario(dto.usuario(), senhaEncriptada);

        validacao(novoUsuario);

        repository.save(novoUsuario);
        return new LoginDtoResponse(novoUsuario, dto.senha());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsuario(username);
    }

    private void validacao(Usuario usuario){
        boolean existe = repository.existsByUsuario(usuario.getUsuario());

        if (existe){
            throw new usuarioDuplicadoException("Usuario ja existe!!!");
        }
    }
}
