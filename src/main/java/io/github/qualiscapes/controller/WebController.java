package io.github.qualiscapes.controller;

import java.util.List;

import io.github.qualiscapes.repository.PeriodicoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final PeriodicoRepository repository;

    public WebController(PeriodicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) List<String> tiers,
            @RequestParam(required = false) String search
    ) {
        List<String> allTiers = List.of("A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C");

        model.addAttribute("areas", repository.findDistinctAvaliationAreas());
        model.addAttribute("tiers", allTiers);

        boolean hasArea = area != null && !area.isBlank();
        boolean hasSearch = search != null && !search.isBlank();
        boolean hasTiers = tiers != null && !tiers.isEmpty();

        if (hasArea || hasSearch || hasTiers) {
            String areaParam = hasArea ? area : null;
            String searchParam = hasSearch ? search : null;

            model.addAttribute(
                    "periodicos",
                    repository.findByFilters(areaParam, tiers, hasTiers, searchParam)
            );
        }

        model.addAttribute("areaSelecionada", area);
        model.addAttribute("tiersSelecionados", tiers != null ? tiers : List.of());
        model.addAttribute("buscaTermo", search);

        return "index";
    }
}
