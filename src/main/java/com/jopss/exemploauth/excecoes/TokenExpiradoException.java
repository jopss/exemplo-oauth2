package com.jopss.exemploauth.excecoes;

public class TokenExpiradoException extends ExemploOAuthException {

        public TokenExpiradoException(String message) {
                super(message);
        }

        public TokenExpiradoException(Throwable cause) {
                super(cause);
        }
        
}
