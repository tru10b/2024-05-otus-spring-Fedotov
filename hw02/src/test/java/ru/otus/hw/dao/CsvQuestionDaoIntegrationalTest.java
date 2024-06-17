package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvQuestionDaoIntegrationalTest {

    @Test
    void isExceptionThrowFindAll() {
        TestFileNameProvider appProperties = Mockito.mock(TestFileNameProvider.class);
        Mockito.when(appProperties.getTestFileName()).thenReturn("qustionss.csv");
        boolean error = false;
        var csvQuestionDao = new CsvQuestionDao(appProperties);

        try {
            var out = csvQuestionDao.findAll();
        } catch (QuestionReadException e) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    void isNormalFindAll() {
        Answer answer1 = new Answer("baby don't hurt me", false);
        Answer answer2 = new Answer("just good friends", false);
        Answer answer3 = new Answer("the unselfish loyal,and benevolent concern for the good of another", true);
        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        Question question1 = new Question("What is love?", answers);
        Answer answer4 = new Answer("42", true);
        Answer answer5 = new Answer("undefined", false);
        Answer answer6 = new Answer("big bang", false);
        List<Answer> answers2 = new ArrayList<>();
        answers2.add(answer4);
        answers2.add(answer5);
        answers2.add(answer6);
        Question question2 = new Question("Answer to The Ultimate Question of Life, the Universe and Everything?", answers2);
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);

        TestFileNameProvider appProperties = Mockito.mock(TestFileNameProvider.class);
        Mockito.when(appProperties.getTestFileName()).thenReturn("questions.csv");

        var csvQuestionDao = new CsvQuestionDao(appProperties);
        var out = csvQuestionDao.findAll();
        assertEquals(questions, out);
    }
}