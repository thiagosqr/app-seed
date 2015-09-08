/*
 * ResourceBundle.java
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * <p><b>GoiasResourceBundle</b></p>
 * Classe que gerencia a herança do arquivo de gov.goias.
 *
 * @author Thiago Rios de Siqueira.
 */
public class GoiasResourceBundle extends PropertyResourceBundle {

    /**
     * Método construtor que recebe um InputStream do arquivo de mensagens.
     *
     * @param is InputStream do arquivo de mensagens.
     * @throws IOException Possível exceção a ser lançada cas caso não consiga ler o inputSTream.
     */
    public GoiasResourceBundle(final InputStream is) throws IOException {
        super(getInputStream(is));
        if (is != null) {
            setParent(getBundleParent());
        }
    }

    /**
     * Método que Obtem o ResourceBundle padrao do pacote gov.goias.excecao.Mensagens.
     *
     * @return ResourceBundle ResourceBundle padrão.
     */
    private static ResourceBundle getBundleParent() {
        try {
            final ClassLoader cl = Thread.currentThread().getContextClassLoader();
            final InputStream is = cl.getResourceAsStream("com/github/thiagosqr/mensagens_pt_BR.properties");
            return new PropertyResourceBundle(is);
        } catch (IOException e) {
            return ResourceBundle.getBundle("gov.goias.excecao.Mensagens", new Locale("pt", "BR"));
        }
    }

    /**
     * Método que Obtem o inputStream da aplicação caso seja nulo sera devolvido o inputStream padrao do arquivo de mensagens.
     *
     * @return InputStream InputStream padrão.
     */
    private static InputStreamReader getInputStream(final InputStream is) throws UnsupportedEncodingException {
        if (is == null) {
            final ClassLoader cl = Thread.currentThread().getContextClassLoader();
            return new InputStreamReader(cl.getResourceAsStream("com/github/thiagosqr/mensagens_pt_BR.properties"),"UTF-8");
        } else {
            return new InputStreamReader(is, "UTF-8");
        }
    }

}