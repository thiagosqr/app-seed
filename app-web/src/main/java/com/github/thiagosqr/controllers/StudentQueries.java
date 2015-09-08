package com.github.thiagosqr.controllers;

import com.github.thiagosqr.conf.WebApp;
import com.github.thiagosqr.conf.excecao.GoiasResourceMessage;
import com.github.thiagosqr.entities.Student;
import com.github.thiagosqr.repositories.StudentRepository;
import com.github.thiagosqr.representation.DataTableResponse;
import com.github.thiagosqr.conf.excecao.InfraException;
import com.github.thiagosqr.conf.excecao.negocio.NaoEncontradoException;
import com.github.thiagosqr.view.model.FormStudent;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Controller
@Path("/student")
public class StudentQueries {
  private static final Logger log = Logger.getLogger(StudentQueries.class);

  @Autowired
  StudentRepository students;

  @Context
  protected HttpServletRequest request;

  @GET
  @Path("/{id}")
  @Template(name="student/student_form")
  @ErrorTemplate(name = "/error")
  public FormStudent findOne(@PathParam("id") final  Integer id) throws InfraException, EmptyResultDataAccessException {
      Student s = students.findOne(id);
      return new FormStudent(s.getId(),s.getName(),s.getDob(),s.getSex(),s.getActive());
  }

  @GET
  @Path("/list")
  @Template(name="student/student_list")
  public String listView()  {
    return "";
  }

  @GET
  @Path("/new")
  @Template(name="student/student_form")
  public FormStudent newStudent() {
    return new FormStudent();
  }

  @GET
  @Path("/paginate")
  @Produces({DataTableResponse.json})
  public Response list(@QueryParam("draw") final Integer draw,
                         @QueryParam("start") final Integer start,
                         @QueryParam("length") final Integer length,
                         @QueryParam("search[value]") final String searchValue) {

      final DataTableResponse dtr = new DataTableResponse();
      final List<Map<String, String>> res = new ArrayList<>();
      dtr.setDraw(draw);
      String[] columns = new String[]{"id", "name", "sex", "dob", "active"};
      try{

          final Integer qtTotal = new Long(students.count()).intValue();
          final Map<String,String> searchParams = new HashMap<>();
          if(!searchValue.isEmpty()) {
              searchParams.put(columns[1], searchValue);
          }

          final Integer page = new Double(Math.ceil(start/length)).intValue();
          final Page<Student> listOfStudents = searchValue.isEmpty()?

                  students.findAll(new PageRequest(page,length)) :
                  students.queryFirst10ByName(searchValue, new PageRequest(page,length));


          final Integer qtFiltrada = new Long(listOfStudents.getTotalElements()).intValue();

          if(qtFiltrada > 0){

              listOfStudents.forEach(s -> res.add(s.asMapofValues(
                      (Object v) -> String.format("row_%s", v),
                      "DT_RowId",
                      "id",
                      columns
              )));
          }
          dtr.setRecordsFiltered(qtFiltrada);
          dtr.setData(res);
          dtr.setRecordsTotal(qtTotal);
      }catch (Exception e){
            log.error(e);
            dtr.setError(GoiasResourceMessage.getMessage("msg_erro_dessconhecido"));
      }
      return Response.status(Response.Status.OK).entity(dtr).build();
  }

}