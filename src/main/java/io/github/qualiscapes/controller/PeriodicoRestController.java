package io.github.qualiscapes.controller;

import io.github.qualiscapes.model.Periodico;
import io.github.qualiscapes.service.PeriodicoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/periodicos")
public class PeriodicoRestController {

    private final PeriodicoService service;

    public PeriodicoRestController(PeriodicoService service){
        this.service = service;
    }

    @GetMapping("/areas")
    public List<String> listAreas(){
        return service.getAvailableAreas();
    }

    @GetMapping("/busca")
    public List<Periodico> buscaPorArea(@RequestParam String area) {
        return service.findByArea(area);
    }

}
