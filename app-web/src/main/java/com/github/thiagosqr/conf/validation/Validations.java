package com.github.thiagosqr.conf.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>Título:</b> Validations
 * <br><b>Descrição:</b> Implementação de coleção de validações<br>
 * informando como obter requisição HttpServletRequest.<br>
 * Adiciona também lista de validações específicas do projeto
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 */
@Component
public class Validations extends ValidationSet {

    public Validations(){
        registerRule(MyValidation.class, (Object o) -> {return true;});
    }

    public HttpServletRequest getServletrequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}

