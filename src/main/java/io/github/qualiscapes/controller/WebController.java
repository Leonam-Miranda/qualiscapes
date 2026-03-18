package io.github.qualiscapes.controller;

import io.github.qualiscapes.model.Periodico;
import io.github.qualiscapes.service.PeriodicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Periodico> resultado = service.search(area, tiers, search, pageable);

        model.addAttribute("areas", service.getAvailableAreas());
        model.addAttribute("tiers", service.getAvailableTiers());
        model.addAttribute("periodicos", resultado.getContent());
        model.addAttribute("paginaAtual", resultado.getNumber());
        model.addAttribute("totalPaginas", resultado.getTotalPages());
        model.addAttribute("totalItens", resultado.getTotalElements());
        model.addAttribute("distribuicao", service.buildDistribution(resultado.getContent()));

        model.addAttribute("areaSelecionada", area);
        model.addAttribute("tiersSelecionados", tiers != null ? tiers : List.of());
        model.addAttribute("buscaTermo", search);
        model.addAttribute("tamanhoPagina", size);

        return "index";
    }
}