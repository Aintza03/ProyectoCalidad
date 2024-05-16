package com.ikea.app.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
/**Servidor para pruebas de integracion. */
public class Main {
 
    public static final String BASE_URI = "http://localhost:8080/rest/";

    /**Inicializa el server. */
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("com.ikea.app");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
    /**Main del server de pruebas. */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}
