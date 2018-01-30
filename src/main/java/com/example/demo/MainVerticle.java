package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "products"));
            JsonObject product1 = new JsonObject().put("itemId", "12345").put("name", "Cooler").put("price", "100.0");
            mongoClient.save("products", product1, id -> {
                System.out.println("Inserted id: " + id.result());

              /*  mongoClient.find("products", new JsonObject().put("itemId", "12345"), res -> {
                    System.out.println("Name is " + res.result().get(0).getString("name"));

                    /*mongoClient.remove("products", new JsonObject().put("itemId", "12345"), rs -> {
                        if (rs.succeeded()) {
                            System.out.println("Product removed ");
                        }
                    });

                });
                */
            });
            req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
        }).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }
}
