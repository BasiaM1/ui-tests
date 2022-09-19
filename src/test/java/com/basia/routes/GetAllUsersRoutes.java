package com.basia.routes;

import com.google.common.net.MediaType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import static com.basia.isolation.IsolatedBaseTest.USERS_AS_STRING;
import static org.openqa.selenium.remote.http.Contents.utf8String;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAllUsersRoutes {

    public static Route successfulGetAllUsersRoute() {
        return Route.matching(req -> req.getUri().endsWith("/users"))
                .to(() -> req ->
                        new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.JSON_UTF_8.toString())
                                .setContent(utf8String(USERS_AS_STRING)));
    }

}
