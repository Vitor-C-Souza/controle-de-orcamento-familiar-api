package br.com.vitor.controle_de_orcamento_familiar.domain.repository;

import br.com.vitor.controle_de_orcamento_familiar.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface usuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
}
