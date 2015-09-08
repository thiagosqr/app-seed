package com.github.thiagosqr.conf.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>Título:</b> FormValidation
 * <br><b>Descrição:</b> Habilita a validação de métodos de API<br>
 * em controllers que utilizam JAX-RS. Introduz suporte a Bean Validations<br>
 * no EAP para versão validation-api-1.0.0 e JErsey 2.0.
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FormValidation {

}