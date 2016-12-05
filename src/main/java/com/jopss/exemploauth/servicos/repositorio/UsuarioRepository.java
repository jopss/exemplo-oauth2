package com.jopss.exemploauth.servicos.repositorio;

import com.jopss.exemploauth.modelos.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
        
        @EntityGraph(value = "perfil.permissoes", type = EntityGraph.EntityGraphType.FETCH)
        Usuario findByLogin(String login);
        
        @EntityGraph(value = "perfil.permissoes", type = EntityGraph.EntityGraphType.FETCH)
        Usuario findByLoginAndSenha(String login, String senha);
        
}
