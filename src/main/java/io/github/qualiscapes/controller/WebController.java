package io.github.qualiscapes.controller;

import java.util.List;

import io.github.qualiscapes.model.Periodico;
import org.springframework.ui.Model;
import io.github.qualiscapes.repository.PeriodicoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    private final PeriodicoRepository repository;

    public WebController(PeriodicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String area,
                                     @RequestParam(required = false) String tier) {

        model.addAttribute("areas", repository.findDistinctAvaliationAreas());
        model.addAttribute("tiers", List.of("A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C"));

        if (area != null && !area.isEmpty()) {
            List<Periodico> resultados;

            if (tier != null && !tier.isEmpty()) {
                resultados = repository.findByAvaliationAreaIgnoreCaseAndTierIgnoreCase(area, tier);
                model.addAttribute("tierSelecionado", tier);
            } else {
                resultados = repository.findByAvaliationAreaIgnoreCase(area);
            }

            model.addAttribute("periodicos", resultados);
            model.addAttribute("areaSelecionada", area);
        }

        return "index";
    }
}

