package com.github.thiagosqr.conf;


import com.github.thiagosqr.conf.messages.UniversalMessageResolver;
import com.github.thiagosqr.conf.security.CsrfDialect;
import com.github.thiagosqr.conf.view.model.ViewErrorModel;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.spi.TemplateProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Provider
@Component
public class ThymeleafViewProcessor implements TemplateProcessor<String> {

  @Context
  ServletContext servletContext;

  @Context
  HttpServletRequest httpServletRequest;

  @Context
  HttpServletResponse httpServletResponse;

  TemplateEngine templateEngine;

  public ThymeleafViewProcessor() throws IOException {

    final Properties templateProps = new Properties(System.getProperties());
    templateProps.load(ThymeleafViewProcessor.class.getClassLoader().getResourceAsStream("tl-template.properties"));

    final TemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("LEGACYHTML5");
    templateResolver.setCacheTTLMs(Long.parseLong(templateProps.getProperty("template.cache.timeout.milis")));
    templateResolver.setCacheable(Boolean.parseBoolean(templateProps.getProperty("template.cache.enabled")));
    templateResolver.setCharacterEncoding(templateProps.getProperty("template.encoding"));

    templateEngine = new TemplateEngine();
    templateEngine.setMessageResolver(new UniversalMessageResolver(Locale.getDefault()));
    templateEngine.setTemplateResolver(templateResolver);

    CsrfDialect csrfDialect = new CsrfDialect(() -> ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

    templateEngine.setAdditionalDialects(new HashSet<IDialect>(Arrays.asList(new IDialect[]{csrfDialect})));

  }

  @Override
  public void writeTo(final String s,
                      final Viewable viewable,
                      final MediaType mediaType,
                      final MultivaluedMap<String, Object> multivaluedMap,
                      final OutputStream out) throws IOException {
    out.flush();
    final WebContext context = new WebContext(httpServletRequest,
                                        httpServletResponse,
                                        servletContext,
                                        httpServletRequest.getLocale());

    final Map<String, Object> variables = new HashMap<>();
    final Object viewModel = viewable.getModel();
    if(ViewErrorModel.class.isInstance(viewModel)) {
      variables.put("validations", ((ViewErrorModel) viewModel).getValidations());
      variables.put("it", ((ViewErrorModel) viewModel).getFormParams());
    }else{
      variables.put("it", viewModel);
    }
    variables.put("isUpdate", Pattern.compile("/\\d*$").matcher(httpServletRequest.getRequestURL().toString()).find());
    variables.put("isInsert", httpServletRequest.getRequestURL().toString().endsWith("/novo"));
    context.setVariables(variables);
    final Writer writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
    templateEngine.process(viewable.getTemplateName(), context, writer);
    writer.flush();
  }

  @Override
  public String resolve(final String path, final MediaType mediaType) {
    return path;
  }


}