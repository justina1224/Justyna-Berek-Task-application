package com.crud.kodillatasks.trello.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Getter
public class TrelloConfig {
    @NotNull
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @NotNull
    @Value("${trello.app.key}")
    private String trelloAppKey;

    @NotNull
    @Value("${trello.app.token}")
    private String trelloToken;

    @NotNull
    @Value("${trello.app.username}")
    private String trelloUserName;
}
