package com.booking.utils;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiUtils {
	public static final String BASE_URL = "https://automationintesting.online/api";

	public static Response get(String endpoint, String token) {
		return given().header("Cookie", "token=" + token).get(BASE_URL + endpoint);
	}

	public static Response post(String endpoint, String body) {
		return given().contentType(ContentType.JSON).body(body).post(BASE_URL + endpoint);
	}

	public static Response put(String endpoint, String body, String token) {
		return given().contentType(ContentType.JSON).header("Cookie", "token=" + token).body(body)
				.put(BASE_URL + endpoint);
	}

	public static Response patch(String endpoint, String body, String token) {
		return given().contentType(ContentType.JSON).header("Cookie", "token=" + token).body(body)
				.patch(BASE_URL + endpoint);
	}

	public static Response delete(String endpoint, String token) {
		return given().header("Cookie", "token=" + token).delete(BASE_URL + endpoint);
	}

	public static Response getWithoutAuth(String endpoint) {
		return given().get(BASE_URL + endpoint);
	}
}
