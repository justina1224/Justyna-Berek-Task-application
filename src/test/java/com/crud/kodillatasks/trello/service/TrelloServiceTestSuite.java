package com.crud.kodillatasks.trello.service;

import com.crud.kodillatasks.config.AdminConfig;
import com.crud.kodillatasks.domain.*;
import com.crud.kodillatasks.service.SimpleEmailService;
import com.crud.kodillatasks.service.TrelloService;
import com.crud.kodillatasks.trello.client.TrelloClient;
import com.crud.kodillatasks.trello.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {
    @InjectMocks
    TrelloService trelloService;
    @Mock
    AdminConfig adminConfig;
    @Mock
    TrelloClient trelloClient;
    @Mock
    SimpleEmailService emailService;
    @Mock
    TrelloMapper trelloMapper;

    @Test
    public void testFetchTrelloBoards() {

        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "testList", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "testBoard", trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> listOfBoards = trelloService.fetchTrelloBoards();

        //Then
        Assert.assertNotNull(listOfBoards);
        Assert.assertEquals(1, listOfBoards.size());
        Assert.assertEquals("1", listOfBoards.get(0).getId());
        Assert.assertEquals("testBoard", listOfBoards.get(0).getName());
        Assert.assertEquals("1", listOfBoards.get(0).getLists().get(0).getId());
        Assert.assertEquals("testList", listOfBoards.get(0).getLists().get(0).getName());
        Assert.assertTrue(listOfBoards.get(0).getLists().get(0).isClosed());
    }
    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testCard", "for test usage", "1", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "createdCard",
                new Badges(2, new AttachmentsByType(new Trello(3, 4))),
                "http://test.com");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test_mail@gmail.com");

        //When
        CreatedTrelloCardDto newCardDto = trelloService.createTrelloCard(trelloCardDto);

        //Then
        Assert.assertNotNull(newCardDto);
        Assert.assertEquals("1", newCardDto.getId());
        Assert.assertEquals("createdCard", newCardDto.getName());
        Assert.assertEquals(2, newCardDto.getBadges().getVotes());
        Assert.assertEquals(3, newCardDto.getBadges().getAttachmentsByType().getTrello().getBoard());
        Assert.assertEquals(4, newCardDto.getBadges().getAttachmentsByType().getTrello().getCard());
        Assert.assertEquals("http://test.com", newCardDto.getShortUrl());

        verify(adminConfig, times(1)).getAdminMail();

    }
}
