package com.github.thiagosqr.controllers;

import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Controller;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Controller
@Path("/pages")
public class Pages {

    @GET
    @Path("/{implicit-view-path-parameter}")
    public Viewable get(@PathParam("implicit-view-path-parameter")final String template) {
        return new Viewable(template, this);
    }

}