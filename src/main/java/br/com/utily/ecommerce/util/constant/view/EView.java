package br.com.utily.ecommerce.util.constant.view;

import lombok.Getter;

@Getter
public enum EView {

    LOGIN("login"),
    SIGN_UP("sign-up"),

    LIST("list"),
    DETAILS("details"),
    NEW("new"),
    EDIT("edit"),
    MANAGE("manage"),

    ADMIN_DASHBOARD("dashboard"),

    CHECKOUT_STEP_ONE("one"),
    CHECKOUT_STEP_TWO("two"),
    CHECKOUT_STEP_THREE("three"),
    CHECKOUT_FINISH("finish"),

    CHANGE_PASSWORD("change-password"),

    ERROR_401("401"),
    ERROR_403("403"),
    ERROR_404("404"),
    ERROR_500("500"),

    REDIRECT("redirect:");

    private final String path;

    EView(String path) {
        this.path = path;
    }
}
