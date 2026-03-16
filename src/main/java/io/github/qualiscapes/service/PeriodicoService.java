package io.github.qualiscapes.service;

import io.github.qualiscapes.model.Periodico;
import io.github.qualiscapes.repository.PeriodicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodicoService {

    private final PeriodicoRepository repository;

    public PeriodicoService(PeriodicoRepository repository){
        this.repository = repository;
    }

    public List<Periodico> findByArea(String area) {
        if (area == null || area.trim().isEmpty()){
            return List.of();
        }
        return repository.findByAvaliationAreaIgnoreCase(area.trim());
    }

    public List<String> getAvailableAreas(){
        return repository.findDistinctAvaliationAreas();
    }
}
