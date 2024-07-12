package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;


@SpringBootTest(classes = {ResultServiceImpl.class})
class ResultServiceImplUnitTest {

    @MockBean
    private TestConfig testConfig;

    @MockBean
    private LocalizedIOService ioService;

    @Autowired
    private ResultServiceImpl resultServiceImplTest;

    @Test
    @DisplayName("Result for passed test is shown")
    public void isShowResultPass() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(5);

        Mockito.when(testConfig.getRightAnswersCountToPass()).thenReturn(2);
        Mockito.doNothing().when(ioService).printLineLocalized(Mockito.anyString());

        resultServiceImplTest.showResult(testResult);

        Mockito.verify(ioService).printLineLocalized("ResultService.passed.test");
    }

    @Test
    @DisplayName("Result for failed test is shown")
    public void isShowResultFailed() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(1);

        Mockito.when(testConfig.getRightAnswersCountToPass()).thenReturn(2);
        Mockito.doNothing().when(ioService).printLine(Mockito.anyString());

        resultServiceImplTest.showResult(testResult);

        Mockito.verify(ioService).printLineLocalized("ResultService.fail.test");
    }
}