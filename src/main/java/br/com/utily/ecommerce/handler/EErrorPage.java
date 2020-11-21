package br.com.utily.ecommerce.handler;

import lombok.Getter;

@Getter
public enum EErrorPage {
    UNAUTHORIZED("pages/error/401"),
    ACCESS_DENIED("pages/error/403"),
    NOT_FOUND("pages/error/404"),
    INTERNAL_SERVER_ERROR("pages/error/500");

    private final String path;

    EErrorPage(String path) {
        this.path = path;
    }
}
