package com.jopss.exemploauth.servicos.seguranca;

import com.jopss.exemploauth.modelos.SegurancaAPI;
import com.jopss.exemploauth.modelos.enums.RoleEnum;
import com.jopss.exemploauth.servicos.seguranca.anotacoes.Privado;
import com.jopss.exemploauth.servicos.seguranca.anotacoes.Publico;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SegurancaInterceptor extends HandlerInterceptorAdapter {

        @Autowired
        private SegurancaServico segurancaServico;
        
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if(handler instanceof HandlerMethod){
                        HandlerMethod met = (HandlerMethod) handler;
                        
                        //acesso publico, libera
                        Publico annotPublico = met.getMethodAnnotation(Publico.class);
                        if(annotPublico!=null){
                                return true;
                        }
                        
                        //acesso privado, verifica as roles do usuario com a role configurada no metodo.
                        Privado annotPrivado = met.getMethodAnnotation(Privado.class);
                        if(annotPrivado!=null){
                                segurancaServico.verificaValidadeTokenAdicionandoNoContexto(request);
                                
                                RoleEnum roleConfigurada = annotPrivado.role();
                                SegurancaAPI usuarioLogado = segurancaServico.getUsuarioLogado();
                                if( usuarioLogado.getUsuario().getPerfil().contemRoleOuAdmin(roleConfigurada) ){
                                        return true;
                                }
                        }
                        
                        //se nao eh publico nem privado, nega por padrao.
                        return this.negarAcesso(response);
                }else{
                        return super.preHandle(request, response, handler);
                }
        }
        
        private boolean negarAcesso(HttpServletResponse response) throws IOException{
                response.getWriter().println("Sem acesso ao recurso.");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
        }
        
}
