package com.jopss.exemploauth.excecoes;

public class UsuarioOuSenhaInvalidaException extends ExemploOAuthException {

        public UsuarioOuSenhaInvalidaException(String message) {
                super(message);
        }

        public UsuarioOuSenhaInvalidaException(Throwable cause) {
                super(cause);
        }
        
}
