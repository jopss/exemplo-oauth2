package com.jopss.exemploauth.excecoes;

public class TokenInvalidoException extends ExemploOAuthException {

        public TokenInvalidoException(String message) {
                super(message);
        }

        public TokenInvalidoException(Throwable cause) {
                super(cause);
        }
        
}
