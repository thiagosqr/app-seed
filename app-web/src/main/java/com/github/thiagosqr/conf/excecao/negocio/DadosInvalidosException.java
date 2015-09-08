/*
 * DadosInvalidosException.java
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
 * <p><b>DadosInvalidosException</b></p>
 * Exceção lançada quando alguma validação relacionada a algum tipo de dados não foi satisfeita.
 *
 * @author Thiago Rios de Siqueira.
 */
public class DadosInvalidosException extends NegocioException {

    /**
     * Construtor padrão para mensagem de negócio.
     */
    public DadosInvalidosException() {
        super("msg_default_dados_invalidos");
    }

}