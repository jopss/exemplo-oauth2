package com.jopss.exemploauth.excecoes;

import com.jopss.exemploauth.modelos.SegurancaAPI;


public class TokenCriadoException extends ExemploOAuthException {

        private SegurancaAPI segurancaAPI;
        
        public TokenCriadoException(SegurancaAPI segurancaAPI) {
                super("Token ja criado para este usuario.");
                this.segurancaAPI = segurancaAPI;
        }

        public SegurancaAPI getSegurancaAPI() {
                return segurancaAPI;
        }
}
