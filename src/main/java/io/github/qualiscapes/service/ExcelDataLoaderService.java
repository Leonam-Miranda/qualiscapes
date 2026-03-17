package io.github.qualiscapes.service;

import io.github.qualiscapes.exception.ExcelProcessingException;
import io.github.qualiscapes.model.Periodico;
import io.github.qualiscapes.repository.PeriodicoRepository;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

@Service
public class ExcelDataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDataLoaderService.class);
    private final PeriodicoRepository repository;

    public ExcelDataLoaderService(PeriodicoRepository repository){
        this.repository=repository;
    }

    @PostConstruct
    public void init(){
        loadExcelData("qualis_capes.xlsx");
    }

    public void loadExcelData( String fileName){
        if(repository.count() > 0){
            System.out.println("Os dados já foram carregados anteriormente");
            return;
        }

        System.out.println("Iniciando a leitura do arquivo " + fileName + "...");
        List<Periodico> periodicosToSave = new ArrayList<>();

        try(InputStream inputStream = new ClassPathResource(fileName).getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)){

            Sheet sheet = workbook.getSheetAt(0);

            DataFormatter formatter = new DataFormatter();

            for (Row row: sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String issn = formatter.formatCellValue(row.getCell(0)).trim();
                String titulo = formatter.formatCellValue(row.getCell(1)).trim();
                String areaAvaliacao = formatter.formatCellValue(row.getCell(2)).trim();
                String tier = formatter.formatCellValue(row.getCell(3)).trim();

                if(!issn.isEmpty()) {
                    Periodico periodico = new Periodico(titulo, issn, areaAvaliacao, tier);
                    periodicosToSave.add(periodico);
                }
            }
            repository.saveAll(periodicosToSave);
            System.out.println("Concluído: " + periodicosToSave.size() + " periodicos salvos no banco de dados.");

        } catch (Exception e){
            logger.error("Erro crítico ao processar Excel: ", e);
            throw new ExcelProcessingException("O arquivo de periódicos está inválido ou mal formatado");
        }
    }
}
