package com.booking.utils;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ApiUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ApiUtils.class);
    private ApiUtils() {}
    public static String baseUrl() { return Config.get("base.url"); }
    private static RequestSpecification request(String token) {
        RequestSpecification request = given().baseUri(baseUrl()).accept(ContentType.JSON);
        if (token != null && !token.isBlank()) request.cookie("token", token);
        return request;
    }
    public static Response get(String endpoint, String token) { return log("GET", endpoint, request(token).get(endpoint)); }
    public static Response getWithoutAuth(String endpoint) { return get(endpoint, null); }
    public static Response post(String endpoint, Object body) {
        RequestSpecification request = request(null).contentType(ContentType.JSON);
        return log("POST", endpoint, body == null ? request.post(endpoint) : request.body(body).post(endpoint));
    }
    public static Response put(String endpoint, Object body, String token) {
        return log("PUT", endpoint, request(token).contentType(ContentType.JSON).body(body).put(endpoint));
    }
    public static Response patch(String endpoint, Object body, String token) {
        return log("PATCH", endpoint, request(token).contentType(ContentType.JSON).body(body).patch(endpoint));
    }
    public static Response delete(String endpoint, String token) {
        return log("DELETE", endpoint, request(token).delete(endpoint));
    }
    private static Response log(String method, String endpoint, Response response) {
        LOG.info("{} {} -> HTTP {}", method, endpoint, response.statusCode());
        return response;
    }
    public static String loginToken() {
        Response response = post("/auth/login", TestData.credentials());
        response.then().statusCode(200);
        String token = response.jsonPath().getString("token");
        if (token == null || token.isBlank()) throw new AssertionError("Authentication token missing");
        return token;
    }
}
