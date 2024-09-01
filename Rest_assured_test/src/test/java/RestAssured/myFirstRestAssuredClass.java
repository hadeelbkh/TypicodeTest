package RestAssured;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;

public class myFirstRestAssuredClass {

	public static void getResponseBody() {
		given().queryParam("CUSTOMER_ID", "13307").queryParam("PASSWORD", "hadeelhadeel15")
				.queryParam("Account_No", "137343").when().get("http://demo.guru99.com/V4/index.php").then()
				.log().body();
	}

	public static void getResponseStatus() {
		int statusCode = given().queryParam("CUSTOMER_ID", "13307").queryParam("PASSWORD", "hadeelhadeel15")
				.queryParam("Account_No", "137343").when().get("http://demo.guru99.com/V4/index.php")
				.getStatusCode();

		System.out.println("The response status is: " + statusCode);

		given().when().get("http://demo.guru99.com/V4/index.php").then().assertThat().statusCode(200);
	}

	public static void getResponseHeaders() {
		System.out.println("The headers in the response: "
				+ get("http://demo.guru99.com/V4/index.php").then().extract().headers());

	}

	public static void getResponseTime() {
		System.out.println("The time taken to fetch the response: "
				+ get("http://demo.guru99.com/V4/index.php").timeIn(TimeUnit.MILLISECONDS)
				+ " milliseconds.");
	}

	public static void getResponseContentType() {
		System.out.println("The content type of the response: "
				+ get("http://demo.guru99.com/V4/index.php").then().extract().contentType());

	}

	public static void getSpecificPartOfResponseBody() {
		String responseBody = when().get("http://demo.guru99.com/V4/index.php").then().extract().asString(); 

		// Check if the response is HTML indicating an error
		if (responseBody.contains("Login Credentials Incorrect")) {
			System.out.println("Login failed. Please check your credentials.");
		} else {
			try {
				// Assuming a valid response, let's try to parse it correctly
				ArrayList<String> amounts = when().get("http://demo.guru99.com/V4/index.php").then()
						.extract().path("result.statements.AMOUNT");

				int sumOfAll = 0;

				for (String a : amounts) {
					System.out.println("The amount value fetched is " + a);
					sumOfAll += Integer.valueOf(a);
				}

				System.out.println("The total amount is " + sumOfAll);
			} catch (ClassCastException e) {
				System.out.println(
						"Unable to cast response to ArrayList. The response might not be in the expected format.");
			}
		}
	}

	public static void main(String[] args) {
		getResponseBody();
		getResponseStatus();
		getResponseHeaders();
		getResponseTime();
		getResponseContentType();
		getSpecificPartOfResponseBody();
	}

}
