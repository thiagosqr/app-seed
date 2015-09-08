package com.github.thiagosqr.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.*;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Path("/")
@AtmosphereService(
        dispatch = true,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class, TrackMessageSizeInterceptor.class},
        path = "/broadcast",
        servlet = "org.glassfish.jersey.servlet.ServletContainer")
public class WebsocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Context
    private HttpServletRequest request;

    private static ScheduledExecutorService scheduler = null;

    private static Double maxCanvasSize = 300D;

    private static final Map<String,List<Double>> clientData = new HashMap<>();

    private AtmosphereResourceFactory resourceFactory;

    @GET
    public Response configureAtmosphereResource() {

        final AtmosphereResource r = (AtmosphereResource) request.getAttribute(ApplicationConfig.ATMOSPHERE_RESOURCE);

        if(resourceFactory == null) {
            resourceFactory = r.getAtmosphereConfig().resourcesFactory();
        }

        if(scheduler == null){
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new RandomDataGenerator(resourceFactory), 0, 5, TimeUnit.SECONDS);
        }

        resourceFactory.registerUuidForFindCandidate(r);
        clientData.put(r.uuid(), new ArrayList<Double>());

        if (r != null) {
            r.addEventListener(new AtmosphereResourceEventListenerAdapter.OnDisconnect() {
                @Override
                public void onDisconnect(AtmosphereResourceEvent event) {
                    if (event.isCancelled()) {
                        logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
                    } else if (event.isClosedByClient()) {
                        logger.info("Browser {} closed the connection", event.getResource().uuid());
                    }

                    resourceFactory.remove(event.getResource().uuid());
                    clientData.remove(event.getResource().uuid());
                }
            });
        } else {
            throw new IllegalStateException();
        }

        return Response.noContent().build();
    }


    /**
     * Recebe o tamanho do canvas do browser para geração de valores
      * @param message
     */
    @POST
    public void broadcast(String message) {
        JsonParser parser = new JsonParser();
        JsonObject o = (JsonObject)parser.parse(message);
        maxCanvasSize = Double.parseDouble(o.get("maxCanvasSize").getAsString());
    }

    /**
     * Gera valores randomicos de X e Y para gráfico linear
     */
    class RandomDataGenerator implements Runnable {

        private Gson gson = new GsonBuilder().create();

        private AtmosphereResourceFactory resourceFactory;

        public RandomDataGenerator(AtmosphereResourceFactory resourceFactory){
            this.resourceFactory = resourceFactory;
        }

        public void run() {

            try {

                    resourceFactory.findAll().stream().forEach(r -> {

                        if (clientData.get(r.uuid()).size() > 0) {
                            clientData.get(r.uuid()).remove(0);
                        }

                        while (clientData.get(r.uuid()).size() < maxCanvasSize) {
                            Double previous = clientData.get(r.uuid()).size() > 0 ? clientData.get(r.uuid()).get(clientData.get(r.uuid()).size() - 1) : 50D;
                            Double y = previous + Math.random() * 10 - 5;
                            clientData.get(r.uuid()).add(y < 0 ? 0 : y > 100 ? 100 : y);
                        }

                        final List<Double[]> res =
                                clientData.get(r.uuid()).stream().map(d -> new Double[]{new Double(clientData.get(r.uuid()).indexOf(d)), d})
                                        .collect(Collectors.toList());

                        r.getBroadcaster().broadcast(gson.toJson(res),r);

                    });

            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

        }
    }



}
