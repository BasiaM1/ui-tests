package com.basia.routes;

import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

public class EditUserRoute {

    public static Route successfullyEditUser(String username) {
        return Route.matching(req -> req.getUri().endsWith("/users/" + username) && req.getMethod().equals(HttpMethod.PUT))
                .to(() -> req ->
                        new HttpResponse()
                                .setStatus(200)
                );


    }
}
