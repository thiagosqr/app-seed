package com.github.thiagosqr.test.integration;

import com.github.thiagosqr.entities.Student;
import com.github.thiagosqr.test.URLBuilder;
import com.google.gson.Gson;
import cucumber.api.CucumberOptions;
import cucumber.api.java8.En;
import cucumber.api.junit.Cucumber;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true,
        features = "classpath:features/integration/student_crud.feature", //feature:x:n -> x,n = numero das linhas
        format = {"pretty",
                "json:target/target_json/cucumber.json",
                "junit:target/target_junit/cucumber.xml",
                "html:target/cucumber"})
public class ITStudent implements En {

    private final Client client = ClientBuilder.newClient();

    public WebTarget getStudentResource(){
        target = client.target(URLBuilder.getInstance().build()).path("student");
        return target.property(ClientProperties.FOLLOW_REDIRECTS, false);
    }

    private WebTarget target;
    private Response response;
    private String responseAsString;
    private Student student;

    public ITStudent()  {

        Given("that a new student needs to be inserted", () -> {
            student = createStudent();
        });

        When("the student form is filled and submitted to the system", () -> {

            final Form form = formWithStudent(student);
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        });

        Then("the system responds with a http 303 status", () -> {
            assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
        });

        And("response header Locataion containing the URL for the new student", () -> {

            assertTrue(response.getHeaderString("Location").contains("/student/"));
            target = client.target(response.getHeaderString("Location"));
            response = target.request().get();
            assertTrue(response.getStatus() == 200);

        });


        Given("that there is a student with name missing", () -> {

            student = createStudent();
            student.setName(null);

        });

        When("the student form is filled with name missing and submitted to the system", () -> {

            final Form form = formWithStudent(student);
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        });

        Then("the system responds with http status 422 for missing student name", () -> {
            assertTrue(response.getStatus() == 422);
        });

        //Scenario: Check for date of birth in the future
        Given("that there is a student with date of birth in the future", () -> {

            try {

                student = createStudent();
                student.setDob(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2101"));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        When("the student from is filled with dob in the future and submitted to the system", () -> {

            final Form form = formWithStudent(student);
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        });

        Then("the system responds with http status 422 for student dob in the future", () -> {
            assertTrue(response.getStatus() == 422);
        });
        //Scenario: Check for date of birth in the future


        //Scenario: Check for null sex
        Given("there is a student with sex as null", () -> {
            student = createStudent();});

        When("the student form is filled with sex as null and submitted to the system", () -> {

            final Form form = new Form()
                    .param("name", student.getName())
                    .param("dob", new SimpleDateFormat("MM/dd/yyyy").format(student.getDob()))
                    .param("active", String.valueOf(student.getActive()));

            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        });

        Then("the system responds with http status 422 for student sex as null", () -> {
            assertTrue(response.getStatus() == 422);
        });
        //Scenario: Check for null sex


        //Scenario: Read student
        Given("there is a student to be read", () -> {

            final Form form = formWithStudent(createStudent());
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
            assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
            assertTrue(response.getHeaderString("Location").contains("/student/"));
            target = client.target(response.getHeaderString("Location"));

        });

        When("the URL containing student's id is accessed", () -> {
            response = target.request().get();
        });

        Then("the system responds with http status 200", () -> {
            assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
        });
        //Scenario: Read student


        //Scenario: Delete student
        Given("there is a student to be deleted", () -> {

            final Form form = formWithStudent(createStudent());
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
            assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
            assertTrue(response.getHeaderString("Location").contains("/student/"));
            target = client.target(response.getHeaderString("Location"))
                    .property(ClientProperties.FOLLOW_REDIRECTS, false)
                    .path("delete");
        });

        When("the URL related to student deletion is accessed", () -> {
            response = target.request().get();
        });

        Then("the system responds with http status 303 for student deletion", () -> {
            assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
        });

        But("for new requests with the same URL the system responds with http status 404", () -> {
            response = target.request().get();
            assertTrue(response.getStatus() == Response.Status.NOT_FOUND.getStatusCode());
        });
        //Scenario: Delete student


        //Scenario: Update student
        Given("there is a student to be updated", () -> {

            student = createStudent();
            final Form form = formWithStudent(student);
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
            assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
            assertTrue(response.getHeaderString("Location").contains("/student/"));
            target = client.target(response.getHeaderString("Location")).property(ClientProperties.FOLLOW_REDIRECTS, false);

        });

        When("student information is updated using student form and submitted to the system", () -> {
            final String id = response.getHeaderString("Location").split("/")[6];
            student.setName("Marcelo");
            final Form form = formWithStudent(createStudent()).param("id", id);
            response = getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        });

        Then("the system responds for student update with http status 303", () -> {
            assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
        });
        //Scenario: Update student


        //Scenario: List students
        Given("there is a collection of student", () -> {

            IntStream.range(0, 10).parallel().forEach(

                    nbr -> {

                        final Form form = formWithStudent(createStudent());
                        Response response =
                                getStudentResource().request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                        assertTrue(response.getStatus() == Response.Status.SEE_OTHER.getStatusCode());
                    }
            );


        });

        When("the first page of the collection containing 5 students is requested", () -> {

            responseAsString = getStudentResource().path("paginate")
                    .queryParam("draw", "1234")
                    .queryParam("start", 0)
                    .queryParam("length", 5)
                    .queryParam("search[value]", "").request()
                    .get(String.class);

        });

        Then("the system responds with a json object containing the 5 students", () -> {
            parseJson();});

        And("such json object contains the ajax request id", () -> {
            assertTrue(((Double) parseJson().get("draw")) == 1234D);
        });
        And("the total number if students requested for the query", () -> {
            assertTrue(((ArrayList) parseJson().get("data")).size() == 5);
        });
        And("a list containing 5 students", () -> {
            assertTrue(((Double) parseJson().get("recordsTotal")) > 0);
        });
        //Scenario: List students

    }

    private Student createStudent(){

        try {

            final Date d = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2001");
            return new Student(null, "João", d, "M", true);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    private Form formWithStudent(Student a){
        return new Form()
                .param("name", a.getName())
                .param("dob", new SimpleDateFormat("MM/dd/yyyy").format(a.getDob()))
                .param("active", String.valueOf(a.getActive()))
                .param("sex", a.getSex());
    }

    private Map<String, Object> parseJson(){
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        return (Map<String, Object>) gson.fromJson(responseAsString, map.getClass());
    }


}