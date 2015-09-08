/*
 * NaoEncontradoException.java
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
package com.github.thiagosqr.conf.excecao.negocio;

import com.github.thiagosqr.conf.excecao.NegocioException;

/**
 * <p><b>NaoEncontradoException</b></p>
 * Exceção a ser lançada quando não foi possível encontrar o recurso solicitado.
 *
 * @author Thiago Rios de Siqueira.
 */
public class NaoEncontradoException extends NegocioException {

    public NaoEncontradoException() {
        super("msg_default_nao_encontrado");
    }

}