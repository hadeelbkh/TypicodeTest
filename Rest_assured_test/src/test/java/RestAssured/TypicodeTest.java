package RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.http.ContentType.JSON;

public class TypicodeTest {

    static String url = "https://jsonplaceholder.typicode.com/posts";

    // TC 1: Retrieve a list of all books.
    @Test
    public static void getAllBooks() {
        // Verify that the API returns a status code of 200 and contains at least one book
        given().when().get(url).then().assertThat().statusCode(200).body("size()", greaterThan(0));

        // Return a JSON response consisting of a collection of books
        Response response = given().when().get(url);

        // Print the response.
        response.prettyPrint();
    }

    // TC 2: Retrieve details of a specific book by its ID.
    @Test
    public static void getABookByID() {
        int bookID = 2;

        // Send a GET request to the API end-point.
        Response response = given().pathParam("id", bookID).when().get(url + "/{id}");

        // Ensure the status code is 200. And the response id matches the requested bookID.
        response.then().assertThat().statusCode(200).body("id", equalTo(bookID));

        // Print the response.
        response.prettyPrint();
    }

    // TC 3: Add a new book to the collection.
    @Test
    public static void addABook() {
        String book = "{ \"userId\": 11, \"title\": \"1984\", \"body\": \"The old brother\" }";

        // Send a POST request to add the new book
        Response response = given().contentType(JSON).body(book).when().post(url);
        
        // Verify the response.
        response.then().assertThat().statusCode(201);
        
        // Print the response.
        response.prettyPrint();
        System.out.println("SUCCESS: The new book was added.");
    }

    // TC 4: Update details of a specific book by its ID.
    @Test
    public static void updateBook() {
        int bookID = 2;
        String updatedBook = "{ \"title\": \"The spy\" }";
        
        // Send a PUT request to update the book
        Response response = given().contentType(JSON).body(updatedBook).pathParam("id", bookID).when().put(url + "/{id}");
        
        // Verify the response.
        response.then().assertThat().statusCode(200);
        
        // Print the response.
        response.prettyPrint();
        System.out.println("SUCCESS: The book of id " + bookID + " was updated.");
    }
    
    // TC 5: Delete a book from the collection by its ID.
    @Test
    public static void deleteBook() {
        int bookID = 3;
        
        // Send a DELETE request to delete the book by its ID.
        Response response = given().pathParam("id", bookID).when().delete(url + "/{id}");
        
        // Verify the response.
        response.then().assertThat().statusCode(200);
        
        System.out.println("SUCCESS: The book of id " + bookID + " was deleted.");
    }
    
    // TC 6: Error scenario: Request a book with an invalid ID.
    @Test
    public static void getBookWithInvalidID() {
        int invalidBookID = 111; 

        // Send a GET request for a book ID that do not exist.
        Response response = given().pathParam("id", invalidBookID).when().get(url + "/{id}");

        // Verify that the status code is 404 (Not Found)
        response.then().assertThat().statusCode(404);
        
        // Print the response
        response.prettyPrint();
        System.out.println("ERROR: No book found with ID " + invalidBookID);
    }
    
    public static void main(String[] args) {
        getAllBooks();
        getABookByID();
        addABook();
        updateBook();
        deleteBook();
        getBookWithInvalidID();
    }
}

