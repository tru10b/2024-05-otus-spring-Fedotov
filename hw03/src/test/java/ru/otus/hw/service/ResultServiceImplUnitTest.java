package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;


@ExtendWith(MockitoExtension.class)
class ResultServiceImplUnitTest {

    @Mock
    private TestConfig testConfig;

    @Mock
    private LocalizedIOService ioService;

    @InjectMocks
    private ResultServiceImpl resultServiceImplTest;

    @Test
    public void isShowResultPass() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(5);

        Mockito.when(testConfig.getRightAnswersCountToPass()).thenReturn(2);
        Mockito.doNothing().when(ioService).printLineLocalized(Mockito.anyString());

        resultServiceImplTest.showResult(testResult);

        Mockito.verify(ioService).printLine("Congratulations! You passed test!");
    }

    @Test
    public void isShowResultFailed() {
        Student student = new Student("Ivan", "Ivanov");
        TestResult testResult = new TestResult(student);
        testResult.setRightAnswersCount(1);

        Mockito.when(testConfig.getRightAnswersCountToPass()).thenReturn(2);
        Mockito.doNothing().when(ioService).printLine(Mockito.anyString());

        resultServiceImplTest.showResult(testResult);

        Mockito.verify(ioService).printLine("Sorry. You fail test.");
    }
}