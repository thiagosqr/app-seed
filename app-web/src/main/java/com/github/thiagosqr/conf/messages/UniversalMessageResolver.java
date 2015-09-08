package com.github.thiagosqr.conf.messages;

import org.apache.log4j.Logger;
import org.thymeleaf.messageresolver.StandardMessageResolver;

import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;

/**
 * <b>Título:</b> UniversalMessageResolver
 * <br><b>Descrição:</b> Definie uma resolução de mensagens<br>
 * que pode ser reutilizada em todos templates. Ao inves<br>
 * do padrão thymeleaf que é um properties de mensagem para cada<br>
 * template.
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 * @author Thiago Rios de Siqueira
 */
public class UniversalMessageResolver extends StandardMessageResolver {

    static Logger logger = Logger.getLogger(UniversalMessageResolver.class);

    private Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

    public UniversalMessageResolver() {
    }

    public UniversalMessageResolver(Properties p) {
        this.p = p;
    }

    public UniversalMessageResolver(Locale locale) {
        this.locale = locale;
    }

    private Properties p = new Properties();

    protected Properties unsafeGetDefaultMessages() {
        return p.isEmpty()? loadProps()  : p;
    }

    private Properties loadProps() {

        final ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try {
            p.load(new InputStreamReader(cl.getResourceAsStream(
                            String.format("com/github/thiagosqr/mensagens_%s.properties", locale)), "UTF-8")
            );
        } catch (Exception e) {
            logger.error(e);
            p = super.unsafeGetDefaultMessages();
        }

        return p;

    }


}
