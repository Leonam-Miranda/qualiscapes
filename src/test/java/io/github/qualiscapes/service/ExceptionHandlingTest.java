package io.github.qualiscapes.service;

import io.github.qualiscapes.exception.ExcelProcessingException;
import io.github.qualiscapes.repository.PeriodicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ExceptionHandlingTest {

    @Autowired
    private ExcelDataLoaderService excelService;

    @Autowired
    private PeriodicoRepository repository;

    @Test
    @DisplayName("Should throw ExcelProcessingException when the spreadsheet has an invalid structure")
    void shouldThrowExceptionWhenFileIsInvalid() {
        assertThrows(ExcelProcessingException.class, () -> {
            excelService.loadExcelData("invalid_qualis.xlsx");
        });
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should throw ExcelProcessingException when the file does not exist")
    void shouldThrowExceptionWhenFileIsMissing() {
        assertThrows(ExcelProcessingException.class, () -> {
            excelService.loadExcelData("file_that_does_not_exist.xlsx");
        });
    }
}