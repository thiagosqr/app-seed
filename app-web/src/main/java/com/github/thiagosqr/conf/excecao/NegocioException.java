/*
 * NegocioException.java
 *
 * Criado pela SUPART - Supervião de Arquitetura.
 * Artefato integrante dos ativos de Infra-Estrutura de aplicações JAVA.
 *
 * Estado de Goiás 2014.
 *
 *   _____       _   __
 *  / ____|     (_) /_/
 * | |  __  ___  _  __ _ ___
 * | | |_ |/ _ \| |/ _` / __|
 * | |__| | (_) | | (_| \__ \
 *  \_____|\___/|_|\__,_|___/
 *
 *
 * Todos os direitos estão reservados.
 */

package com.github.thiagosqr.conf.excecao;


public abstract class NegocioException extends Exception {


    protected NegocioException(final String mensagem, final Object... parametros) {
        super(GoiasResourceMessage.getMessage(mensagem, parametros));
    }

}