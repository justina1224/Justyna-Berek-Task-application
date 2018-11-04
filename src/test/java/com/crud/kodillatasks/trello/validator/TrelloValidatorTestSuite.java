package com.crud.kodillatasks.trello.validator;

import com.crud.kodillatasks.domain.TrelloBoard;
import com.crud.kodillatasks.domain.TrelloCard;
import com.crud.kodillatasks.domain.TrelloList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTestSuite {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void testValidateCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("testCard", "for test usage", "1", "1");
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        //When
        trelloValidator.validateCard(trelloCard);

        //Then
        assertTrue(buffer.toString().contains("Someone is testing my app!"));
        System.setOut(original);
    }

    @Test
    public void testValidateTrelloBoards() {

        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "newBoard", new ArrayList<TrelloList>());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "test", new ArrayList<TrelloList>());
        TrelloBoard trelloBoard3 = new TrelloBoard("3", "emptyBoard", new ArrayList<TrelloList>());

        List<TrelloBoard> listOfBoards = new ArrayList<>();
        listOfBoards.add(trelloBoard1);
        listOfBoards.add(trelloBoard2);
        listOfBoards.add(trelloBoard3);

        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(listOfBoards);

        //Then
        Assert.assertNotNull(filteredList);
        Assert.assertEquals(2, filteredList.size());
    }
}
