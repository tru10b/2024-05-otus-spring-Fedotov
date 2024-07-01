package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        ioService.printLine("");
        ioService.printFormattedLineLocalized("TestService.answer.the.questions");

        for (var question : questions) {
            printQuestion(question);
            var answerNumber = getAnswer(question);
            testResult.applyAnswer(question, isCorrectAnswer(question, answerNumber));
        }

        return testResult;
    }

    private void printQuestion(Question question) {
        ioService.printLine(question.text());
        if (!question.answers().isEmpty()) {
            ioService.printLineLocalized("TestService.list.answers");
            for (int i = 0; i < question.answers().size(); i++) {
                int j = i + 1;
                ioService.printLine(j + " " + question.answers().get(i).text());
            }
        }
    }

    private int getAnswer(Question question) {
        return ioService.readIntForRangeWithPromptLocalized(1, question.answers().size(),
                "TestService.choose.answers", "TestService.wrong.input");
    }

    private boolean isCorrectAnswer(Question question, int answerNumber) {
        return question.answers().get(answerNumber - 1).isCorrect();
    }

}
