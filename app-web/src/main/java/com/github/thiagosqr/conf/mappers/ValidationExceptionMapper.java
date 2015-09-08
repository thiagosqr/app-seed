
package com.github.thiagosqr.conf.mappers;

import com.github.thiagosqr.conf.view.model.ViewErrorModel;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;

/**
 * <b>Título:</b> ValidationExceptionMapper
 * <br><b>Descrição:</b> Mapeamento da exceção ValidationException<br>
 * para que responda código 422 e retorne para pagina original da requisição<br>
 * e apresente mensagens de validação para usuário
 * <br><b>Copyright:</b> Copyright(c) 2015
 * <br><b>Empresa:</b> SEGPLAN
 */
@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
	private static final Logger log = Logger.getLogger(ValidationExceptionMapper.class);

	@Context
	protected HttpServletRequest request;

	@Override
	public Response toResponse(final ValidationException exception) {
		return Response.status(422).entity(new Viewable((String) request.getAttribute("errorTemplate"), getErrorModel(exception))).build();
	}

	protected ViewErrorModel getErrorModel(final ValidationException exception){
		final ViewErrorModel vem = new ViewErrorModel();
		vem.setFormParams((Map<String, Object>) request.getAttribute("formParams"));
		vem.setValidations((Map<String, List<String>>) request.getAttribute("validations"));
		return vem;
	}

}
