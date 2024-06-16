package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = false;
            ioService.printLine(question.text());
            if (!question.answers().isEmpty()) {
                ioService.printLine("List of answers:");
                for (int i = 0; i < question.answers().size(); i++) {
                    ioService.printLine(i + " " + question.answers().get(i).text());
                }
            }

            var answerNumber = ioService.readIntForRangeWithPrompt(0, question.answers().size() - 1,
                    "Choose your answer:", "Wrong input");
            if (question.answers().get(answerNumber).isCorrect()) {
                isAnswerValid = true;
            }
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
