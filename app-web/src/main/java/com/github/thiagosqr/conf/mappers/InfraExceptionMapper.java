
package com.github.thiagosqr.conf.mappers;

import com.github.thiagosqr.conf.WebApp;
import com.github.thiagosqr.conf.excecao.GoiasResourceMessage;
import com.github.thiagosqr.conf.excecao.InfraException;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * <b>Título:</b> InfraExceptionMapper
 * <br><b>Descrição:</b> Mapeamento da exceção InfraException<br>
 * para que responda código 500 e mensagem genérica
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 */
@Component
@Provider
public class InfraExceptionMapper implements ExceptionMapper<InfraException> {
	private static final Logger log = Logger.getLogger(InfraExceptionMapper.class);
	@Context
	protected HttpServletRequest request;

	@Override
	public Response toResponse(final InfraException infraException) {
		log.error(infraException);
		return Response.status(getErrorStatus(infraException)).entity(new Viewable(WebApp.ERROR_TEMPLATE_NAME, getErrorModel(infraException))).build();
	}

	protected String getErrorModel(final InfraException throwable) {
		return GoiasResourceMessage.getMessage("msg_erro_dessconhecido");
	}
	protected Response.Status getErrorStatus(final InfraException throwable) {
		return Response.Status.INTERNAL_SERVER_ERROR;
	}

}
