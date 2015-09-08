/*
 * PaginacaoNaoInformadaException.java
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
 * <p><b>PaginacaoNaoInformadaException</b></p>
 * Exceção lançada quando o usuário não informa dados sobre paginação.
 *
 * @author Thiago Rios de Siqueira.
 */
public class PaginacaoNaoInformadaException extends NegocioException {

    /**
     * <p>Construtor que recebe a chave da mensagem no arquivo de gov.goias.gov.goias.gov.goias.gov
     * e seus parametros que são opcionais.</p>
     * <p/>
     * <p>Caso deseje mostrar uma mensagem fora do arquivo de gov.goias.gov.goias.gov.goias.gov
     * basta apenas passar a mensagem na integra.</p>
     */
    public PaginacaoNaoInformadaException() {
        super("msg_default_paginacao_invalida");
    }

}
