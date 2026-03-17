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
    public String index(Model model,
                        @RequestParam(required = false) String area,
                        @RequestParam(required = false) String tier,
                        @RequestParam(required = false) String search) {

        model.addAttribute("areas", repository.findDistinctAvaliationAreas());
        model.addAttribute("tiers", List.of("A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C"));

        if ((area != null && !area.isEmpty()) || (tier != null && !tier.isEmpty()) || (search != null && !search.isEmpty())) {

            String areaParam = (area != null && !area.isEmpty()) ? area : null;
            String tierParam = (tier != null && !tier.isEmpty()) ? tier : null;
            String searchParam = (search != null && !search.isEmpty()) ? search : null;

            model.addAttribute("periodicos", repository.findByFilters(areaParam, tierParam, searchParam));
            model.addAttribute("areaSelecionada", area);
            model.addAttribute("tierSelecionado", tier);
            model.addAttribute("buscaTermo", search);
        }

        return "index";
    }
}

