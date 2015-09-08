package com.github.thiagosqr.conf.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>Título:</b> NotEmpty
 * <br><b>Descrição:</b> Define annotation para <br>
 * parametros que não podem ser nulos
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 * @see FormValidation
 */@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotEmpty {
}


