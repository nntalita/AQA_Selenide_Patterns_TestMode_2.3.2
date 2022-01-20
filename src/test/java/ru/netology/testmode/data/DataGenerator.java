package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@Data

public class DataGenerator {


    @Value
    public static class User {
        String login;
        String password;
        String status;
    }

    public DataGenerator() {
    }
    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();




    private static void sendRequest(User user) {
        // отправить запрос на указанный в требованиях path
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();

        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static User getUser(String status) {
            User user = new User(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static User getRegisteredUser(String status) {
            User registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }



}
