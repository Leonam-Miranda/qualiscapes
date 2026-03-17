package io.github.qualiscapes.service;

import io.github.qualiscapes.model.Periodico;
import io.github.qualiscapes.repository.PeriodicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodicoService {

    private static final List<String> AVAILABLE_TIERS =
            List.of("A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C");

    private final PeriodicoRepository repository;

    public PeriodicoService(PeriodicoRepository repository) {
        this.repository = repository;
    }

    public List<String> getAvailableAreas() {
        return repository.findDistinctAvaliationAreas();
    }

    public List<String> getAvailableTiers() {
        return AVAILABLE_TIERS;
    }

    public List<Periodico> search(String area, List<String> tiers, String search) {
        String normalizedArea = normalize(area);
        String normalizedSearch = normalize(search);
        boolean applyTierFilter = tiers != null && !tiers.isEmpty();

        return repository.findByFilters(
                normalizedArea,
                tiers,
                applyTierFilter,
                normalizedSearch
        );
    }

    public boolean hasAnyFilter(String area, List<String> tiers, String search) {
        return normalize(area) != null
                || normalize(search) != null
                || (tiers != null && !tiers.isEmpty());
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
