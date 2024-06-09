package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {


    private final IOService ioService;

    private final QuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        // Получить вопросы из дао и вывести их с вариантами ответов
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questionsToPrint = csvQuestionDao.findAll();

        for (Question currentQuestion :
                questionsToPrint) {
            ioService.printLine(currentQuestion.text());

            if (!currentQuestion.answers().isEmpty()) {
                ioService.printLine("Choose your answer:");
                for (Answer currentAnswer :
                        currentQuestion.answers()) {
                    ioService.printLine("- " + currentAnswer.text());
                }
            }
            ioService.printLine("");
        }
    }
}
