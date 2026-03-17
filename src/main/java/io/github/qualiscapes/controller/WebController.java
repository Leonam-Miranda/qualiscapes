package io.github.qualiscapes.controller;

import io.github.qualiscapes.service.PeriodicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    private final PeriodicoService service;

    public WebController(PeriodicoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) List<String> tiers,
            @RequestParam(required = false) String search
    ) {
        model.addAttribute("areas", service.getAvailableAreas());
        model.addAttribute("tiers", service.getAvailableTiers());

        if (service.hasAnyFilter(area, tiers, search)) {
            model.addAttribute("periodicos", service.search(area, tiers, search));
        }

        model.addAttribute("areaSelecionada", area);
        model.addAttribute("tiersSelecionados", tiers != null ? tiers : List.of());
        model.addAttribute("buscaTermo", search);

        return "index";
    }
}
