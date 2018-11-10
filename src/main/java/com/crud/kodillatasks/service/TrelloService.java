package com.crud.kodillatasks.service;

import com.crud.kodillatasks.config.AdminConfig;
import com.crud.kodillatasks.domain.CreatedTrelloCardDto;
import com.crud.kodillatasks.domain.Mail;
import com.crud.kodillatasks.domain.TrelloBoardDto;
import com.crud.kodillatasks.domain.TrelloCardDto;
import com.crud.kodillatasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Tasks: New Trello card";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleEmailService emailService;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(newCard).ifPresent(card -> emailService.sendMime(new Mail(adminConfig.getAdminMail(),
                SUBJECT, "New card: " + card.getName() + " has been created on your Trello account")));

        return newCard;
    }
}
