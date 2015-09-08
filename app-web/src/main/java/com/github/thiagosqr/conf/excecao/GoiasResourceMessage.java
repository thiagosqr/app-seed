/*
 * GoiasResourceMessage.java
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

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p><b>GoiasResourceMessage</b></p>
 * Classe que representa o gerenciador de gov.goias.
 *
 * @author Marcos Fernando Costa.
 */
public final class GoiasResourceMessage {
    private static GoiasResourceBundle rb;

    /**
     * Construtor privado para garantir que uma instância avulsa seja instanciada.
     * Esse método foi criado para satisfazer uma regra do Sonar.
     */
    private GoiasResourceMessage() {
    }

    /**
     * Método estático interno que retorna o Bundle (lista de gov.goias) para ser utilizado para construir as gov.goias.
     * O arquivo deverá se chamar Mensagens.properties e deverá estar obrigatoriamente no pacote go.goias.excecao.
     *
     * @return {@link java.util.ResourceBundle}
     * @throws InfraException Possível exceção a ser lançada caso não consiga recuperar o arquivo de gov.goias.
     */
    private static ResourceBundle getMessages() throws InfraException {
        if (rb == null) {
            try {
                final ClassLoader cl = Thread.currentThread().getContextClassLoader();

                final InputStream is = cl.getResourceAsStream(
                        String.format("com/github/thiagosqr/mensagens_%s.properties", Locale.getDefault()));

                rb = new GoiasResourceBundle(is);
            } catch (final Exception e) {
                throw new InfraException(e);
            }
        }
        return rb;
    }

    /**
     * Método estático que retorna retorna a mensagem com seus parâmetros preenchidos do arquivo de mensagem.
     *
     * @param key        Chave da mensagem no arquivo de gov.goias
     * @param parameters Lista de parâmetros para montar a mensagem de acordo com os parâmetros da mensagem.
     * @return String mensagem montada ou a chave repassada caso a chave não seja encontrada no arquivo de gov.goias.
     */
    public static String getMessage(final String key, final Object... parameters) {
        try {
            final String m = getMessages().getString(key);
            return MessageFormat.format(m, parameters);
        } catch (final Exception e) {
            return key;
        }
    }

}