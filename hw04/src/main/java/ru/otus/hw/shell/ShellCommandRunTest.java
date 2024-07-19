package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommandRunTest {

    private final TestRunnerService testRunnerService;

    @ShellMethod("Run test")
    public String runTest() {
        testRunnerService.run();
        return "Another test?";
    }
}
