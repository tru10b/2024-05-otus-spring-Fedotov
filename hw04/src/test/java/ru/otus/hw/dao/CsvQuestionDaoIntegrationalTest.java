package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = {CsvQuestionDao.class})
class CsvQuestionDaoIntegrationalTest {

    @MockBean
    private TestFileNameProvider appProperties;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    @DisplayName("Exception is thrown")
    public void isExceptionThrowFindAll() {

        Mockito.when(appProperties.getTestFileName()).thenReturn("qustionss.csv");

        assertThrows(QuestionReadException.class,
                () -> csvQuestionDao.findAll());
    }

    @Test
    @DisplayName("All questions from file is found")
    public void isNormalFindAll() {
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

        Mockito.when(appProperties.getTestFileName()).thenReturn("questions.csv");

        var out = csvQuestionDao.findAll();

        assertEquals(questions, out);
    }
}