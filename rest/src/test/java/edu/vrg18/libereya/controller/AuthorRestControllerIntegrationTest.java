package edu.vrg18.libereya.controller;

import edu.vrg18.libereya.entity.AppRole;
import edu.vrg18.libereya.entity.AppUser;
import edu.vrg18.libereya.entity.Author;
import edu.vrg18.libereya.entity.UserRole;
import edu.vrg18.libereya.repository.AuthorRepository;
import edu.vrg18.libereya.repository.RoleRepository;
import edu.vrg18.libereya.repository.UserRepository;
import edu.vrg18.libereya.repository.UserRoleRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthorRestControllerIntegrationTest {

    private static final String BASE_PATH = "/rest/authors";
    private static final String LOGIN = "admin1";
    private static final String PASSWORD = "123";
    private static final String ROLE = "ROLE_ADMIN";
    private static final String TEST_AUTHOR_1 = "Полиграф Полиграфович Шариков";
    private static final String TEST_AUTHOR_2 = "Акакий Акакиевич Башмачкин";

    @LocalServerPort
    private int port;

    private AuthorRepository authorRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private List<UUID> idListForRemoteAfterTest = new ArrayList<>();

    @Autowired
    private void setRepositore(AuthorRepository authorRepository,
                               UserRepository userRepository,
                               RoleRepository roleRepository,
                               UserRoleRepository userRoleRepository) {
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Before
    public void setup() {

        RestAssured.port = port;
        createTestUser();
    }

    @After
    public void restoreDb() {

        idListForRemoteAfterTest.forEach(id -> authorRepository.deleteById(id));
    }

    @Test
    public void whenCreateAuthor_thenStatus201() {

        Author author = new Author(TEST_AUTHOR_1);

        idListForRemoteAfterTest.add(UUID.fromString(
                given().log().body()
                        .contentType(ContentType.JSON).body(author)
                        .auth().preemptive().basic(LOGIN, PASSWORD)
                        .when().post(BASE_PATH)
                        .then().log().body()
                        .statusCode(HttpStatus.OK.value())
                        .body("name", equalTo(TEST_AUTHOR_1))
                        .extract().jsonPath().get("id")));
    }

    @Test
    public void givenAuthor_whenGetAuthor_thenStatus200() {

        String id = createTestAuthor(TEST_AUTHOR_1);

        given().pathParam("id", id)
                .auth().preemptive().basic(LOGIN, PASSWORD)
                .when().get(BASE_PATH + "/{id}")
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(TEST_AUTHOR_1));
    }

    @Test
    public void givenAuthor_whenDeleteAuthor_thenStatus200() {

        String id = createTestAuthor(TEST_AUTHOR_1);

        given().pathParam("id", id).log().body()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic(LOGIN, PASSWORD)
                .when().delete(BASE_PATH + "/{id}")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());

        idListForRemoteAfterTest.remove(idListForRemoteAfterTest.size() - 1);
    }

    @Test
    public void givenNoAuthor_whenGetAuthor_thenStatus404() {

        given().pathParam("id", UUID.randomUUID().toString())
                .auth().preemptive().basic(LOGIN, PASSWORD)
                .when().get(BASE_PATH + "/{id}")
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenUpdateAuthor_thenStatus200() {

        String id = createTestAuthor(TEST_AUTHOR_1);
        Author author = new Author(TEST_AUTHOR_2);
        author.setId(UUID.fromString(id));

        given().log().body()
                .contentType(ContentType.JSON).body(author)
                .auth().preemptive().basic(LOGIN, PASSWORD)
                .when().put(BASE_PATH)
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(TEST_AUTHOR_2));
    }

    @Test
    public void givenAuthors_whenGetAuthors_thenStatus200() {

        String id1 = createTestAuthor(TEST_AUTHOR_1);
        String id2 = createTestAuthor(TEST_AUTHOR_2);

        given()
                .auth().preemptive().basic(LOGIN, PASSWORD)
                .when().get(BASE_PATH)
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .root("find{it.id=='%s'}.name")
                .body(withArgs(id1), equalTo(TEST_AUTHOR_1))
                .body(withArgs(id2), equalTo(TEST_AUTHOR_2));
    }

    private String createTestAuthor(String authorName) {

        UUID authorId = authorRepository.saveAndFlush(new Author(authorName)).getId();
        idListForRemoteAfterTest.add(authorId);
        return authorId.toString();
    }

    private void createTestUser() {

        if (userRepository.findAppUserByUserName(LOGIN) == null) {
            AppUser appUser = userRepository.saveAndFlush(new AppUser(LOGIN, PASSWORD, true));
            AppRole appRole = roleRepository.saveAndFlush(new AppRole(ROLE));
            userRoleRepository.saveAndFlush(new UserRole(appUser, appRole));
        }
    }
}
