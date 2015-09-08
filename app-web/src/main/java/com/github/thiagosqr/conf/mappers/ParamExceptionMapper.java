package com.github.thiagosqr.conf.mappers;

import com.github.thiagosqr.conf.WebApp;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ParamException;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * <b>Título:</b> ParamExceptionMapper
 * <br><b>Descrição:</b> Mapeamento da exceção ParamException<br>
 * para que responda código 400 e mensagem de erro na requisição
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 */
@Component
@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {
	private static final Logger log = Logger.getLogger(ParamExceptionMapper.class);

	@Override
	public Response toResponse(final ParamException exception) {
		log.error(exception);
		return Response.status(getErrorStatus(exception)).entity(new Viewable(WebApp.ERROR_PAGE_URI, getErrorModel(exception))).build();
	}

	protected String getErrorModel(final ParamException exception) {
		return exception.getMessage();
	}
	protected Response.Status getErrorStatus(final ParamException exception) {
		return Response.Status.BAD_REQUEST;
	}

}
