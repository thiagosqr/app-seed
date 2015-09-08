package com.github.thiagosqr.controllers;

import com.github.thiagosqr.entities.Student;
import com.github.thiagosqr.repositories.StudentRepository;
import com.github.thiagosqr.conf.converters.DateParam;
import com.github.thiagosqr.conf.excecao.InfraException;
import com.github.thiagosqr.conf.validation.FormValidation;
import com.github.thiagosqr.conf.validation.NotEmpty;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/student")
public class StudentCmds {
    private static final Logger logger = Logger.getLogger(StudentCmds.class);

    @Autowired
    private StudentRepository students;

    @FormValidation
    @POST
    @ErrorTemplate(name = "student/student_form")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response salvar(@FormParam("id") final Integer id,
                           @NotNull @NotEmpty @FormParam("name") final String name,
                           @NotNull @Past @FormParam("dob") final DateParam dob,
                           @NotNull @FormParam("sex") final String sex,
                           @NotNull @FormParam("active") final boolean active)
            throws InfraException, ValidationException, WebApplicationException {

        final Student student = new Student(id, name,dob.getDate(), sex, active);
        students.save(student);
        return Response.status(Response.Status.SEE_OTHER).header("Location", String.format("student/%s", student.getId())).build();
   }

  @GET
  @Path("/{id}/delete")
  public Response remover(@PathParam("id") final Integer id) throws EmptyResultDataAccessException, InfraException {
        students.delete(id);
        return Response.status(Response.Status.SEE_OTHER).header("Location", "student/list").build();
  }

}