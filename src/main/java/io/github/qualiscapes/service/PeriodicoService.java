package io.github.qualiscapes.service;

import io.github.qualiscapes.model.Periodico;
import io.github.qualiscapes.repository.PeriodicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Page<Periodico> search(String area, List<String> tiers, String search, Pageable pageable) {
        String normalizedArea = normalize(area);
        String normalizedSearch = normalize(search);
        boolean applyTierFilter = tiers != null && !tiers.isEmpty();

        return repository.findByFilters(
                normalizedArea,
                tiers,
                applyTierFilter,
                normalizedSearch,
                pageable
        );
    }

    public Map<String, Long> buildDistribution(List<Periodico> periodicos) {
        Map<String, Long> distribuicao = new LinkedHashMap<>();

        for (String tier : AVAILABLE_TIERS) {
            long count = periodicos.stream()
                    .filter(p -> tier.equals(p.getTier()))
                    .count();

            if (count > 0) {
                distribuicao.put(tier, count);
            }
        }

        return distribuicao;
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}