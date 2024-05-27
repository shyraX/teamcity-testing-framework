package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import lombok.Getter;

import static com.example.teamcity.ui.Selectors.byId;


@Getter
public class LoginPage extends Page {
    private static final String LOGIN_PAGE_URL = "/login.html";
    private final SelenideElement usernameInput = byId("username");
    private final SelenideElement passwordInput = byId("password");

    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        usernameInput.setValue(user.getUsername());
        passwordInput.setValue(user.getPassword());
        submit();
    }
}
