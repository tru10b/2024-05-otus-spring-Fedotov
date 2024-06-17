package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

class ResultServiceImplUnitTest {

    @Test
    void isShowResultPass() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(5);

        TestConfig testConfig = Mockito.mock(TestConfig.class);
        IOService ioService = Mockito.mock(IOService.class);

        Mockito.when(testConfig.getRightAnswersCountToPass()).thenReturn(2);
        Mockito.doNothing().when(ioService).printLine(Mockito.anyString());

        var resultServiceImplTest = new ResultServiceImpl(testConfig, ioService);
        resultServiceImplTest.showResult(testResult);

        Mockito.verify(ioService).printLine("Congratulations! You passed test!");
    }

    @Test
    void isShowResultFailed() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(1);

        TestConfig testConfig = Mockito.mock(TestConfig.class);
        IOService ioService = Mockito.mock(IOService.class);

        Mockito.when(testConfig.getRightAnswersCountToPass()).thenReturn(2);
        Mockito.doNothing().when(ioService).printLine(Mockito.anyString());

        var resultServiceImplTest = new ResultServiceImpl(testConfig, ioService);
        resultServiceImplTest.showResult(testResult);

        Mockito.verify(ioService).printLine("Sorry. You fail test.");
    }
}