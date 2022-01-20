package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() throws InterruptedException {
        var registeredUser = getRegisteredUser("active");
        // попытка входа в личный кабинет с учётными данными зарегистрированного активного пользователя
        $("[name='login']").sendKeys(registeredUser.getLogin());
        $("[name='password']").sendKeys(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2.heading").shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() throws InterruptedException {
        var notRegisteredUser = getUser("active");
        // попытка входа в личный кабинет незарегистрированного пользователя
        $("[name='login']").sendKeys(notRegisteredUser.getLogin());
        $("[name='password']").sendKeys(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() throws InterruptedException {
        var blockedUser = getRegisteredUser("blocked");
        // попытка входа в личный кабинет  заблокированного пользователя
        $("[name='login']").sendKeys(blockedUser.getLogin());
        $("[name='password']").sendKeys(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification_status_error .notification__content").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() throws InterruptedException {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // попытка входа в личный кабинет с неверным логином и верным паролем
              $("[name='login']").sendKeys(wrongLogin);
        $("[name='password']").sendKeys(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() throws InterruptedException {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // попытка входа в личный кабинет с неверным паролем и верным логином
              $("[name='login']").sendKeys(registeredUser.getLogin());
        $("[name='password']").sendKeys(wrongPassword);
        $("[data-test-id='action-login']").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
