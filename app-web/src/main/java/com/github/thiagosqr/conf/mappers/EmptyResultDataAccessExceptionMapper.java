package com.github.thiagosqr.conf.mappers;

import com.github.thiagosqr.conf.WebApp;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class EmptyResultDataAccessExceptionMapper implements ExceptionMapper<EmptyResultDataAccessException> {
	private static final Logger log = Logger.getLogger(EmptyResultDataAccessExceptionMapper.class);

	@Override
	public Response toResponse(final EmptyResultDataAccessException exception) {
		log.error(exception);
		return Response.status(getErrorStatus(exception)).entity(new Viewable(WebApp.ERROR_PAGE_URI, getErrorModel(exception))).build();
	}

	protected String getErrorModel(final EmptyResultDataAccessException exception) {
		return exception.getMessage();
	}
	protected Response.Status getErrorStatus(final EmptyResultDataAccessException exception) {
		return Response.Status.NOT_FOUND;
	}

}
