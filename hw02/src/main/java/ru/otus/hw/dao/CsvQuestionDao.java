package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider appProperties;

    @Override
    public List<Question> findAll() {
        List<QuestionDto> questionsDtoParsed;
        List<Question> questionsParsed = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream streamFromResources = classLoader.getResourceAsStream(appProperties.getTestFileName())) {
            InputStreamReader streamReader = new InputStreamReader(streamFromResources);
            CsvToBeanBuilder<QuestionDto> csvToBeanBuilder = new CsvToBeanBuilder<>(streamReader);
            questionsDtoParsed = csvToBeanBuilder
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .withSeparator(';').build()
                    .parse();
            System.out.println();
        } catch (IOException e) {
            throw new QuestionReadException("Error during questions reading");
        }

        for (QuestionDto questionDto : questionsDtoParsed) {
            questionsParsed.add(questionDto.toDomainObject());
        }

        return questionsParsed;
    }
}
