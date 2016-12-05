package com.jopss.exemploauth.web;

import com.jopss.exemploauth.modelos.SegurancaAPI;
import com.jopss.exemploauth.modelos.enums.RoleEnum;
import com.jopss.exemploauth.servicos.seguranca.SegurancaServico;
import com.jopss.exemploauth.servicos.seguranca.anotacoes.Privado;
import com.jopss.exemploauth.servicos.seguranca.anotacoes.Publico;
import com.jopss.exemploauth.web.util.ExemploOAuthController;
import javax.servlet.http.HttpServletRequest;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/seguranca")
public class SegurancaController extends ExemploOAuthController{
        
        @Autowired
        private SegurancaServico segurancaServico;
        
        @Publico
        @ResponseBody
        @RequestMapping(value = "/logar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String logar(HttpServletRequest request) {
                OAuthResponse response = segurancaServico.logarOAuth(request);
                return response.getBody();
	}
        
        @Privado(role=RoleEnum.ROLE_GERAL)
        @ResponseBody
        @RequestMapping(value = "/usuario/logado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SegurancaAPI retornarUsuarioLogado(HttpServletRequest request) {
                return segurancaServico.getUsuarioLogado();
	}
}
