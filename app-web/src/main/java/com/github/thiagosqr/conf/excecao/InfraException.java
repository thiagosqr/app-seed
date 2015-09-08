/*
 * InfraException.java
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

/**
 * <p><b>InfraException</b></p>
 * Classe que representa a Exceção genérica que não esta prevista no escopo do sistema.
 *
 * @author Marcos Fernando Costa.
 * @see Exception
 */
public class InfraException extends Exception {
    private static final long serialVersionUID = -6472789855877680893L;

    /**
     * Construtor que recebe uma mensagem pura como argumento.
     *
     * @param message mensagem da exceção.
     */
    public InfraException(final String message) {
        super(message);
    }

    /**
     * Construtor que recebe uma mensagem pura como argumento e a exceção de origem.
     *
     * @param message mensagem da exceção.
     * @param cause   exceção original.
     */
    public InfraException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor que recebe como argumento a exceção que originou.
     *
     * @param cause exceção original.
     */
    public InfraException(final Throwable cause) {
        super(cause);
    }

}