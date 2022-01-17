package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

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
//        var registeredUser = getRegisteredUser("active");
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $("[name='login']").sendKeys(registeredUser.getLogin());
        $("[name='password']").sendKeys(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() throws InterruptedException {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $("[name='login']").sendKeys(notRegisteredUser.getLogin());
        $("[name='password']").sendKeys(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() throws InterruptedException {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $("[name='login']").sendKeys(blockedUser.getLogin());
        $("[name='password']").sendKeys(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() throws InterruptedException {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $("[name='login']").sendKeys(wrongLogin);
        $("[name='password']").sendKeys(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() throws InterruptedException {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $("[name='login']").sendKeys(registeredUser.getLogin());
        $("[name='password']").sendKeys(wrongPassword);
        $("[data-test-id='action-login']").click();
        Thread.sleep(2000);
    }
}
