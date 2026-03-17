package io.github.qualiscapes.repository;

import io.github.qualiscapes.model.Periodico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PeriodicoRepository extends JpaRepository<Periodico, Long> {
      List<Periodico> findByAvaliationAreaIgnoreCase(String avaliationArea); //busca por área

      @Query("SELECT DISTINCT p.avaliationArea FROM Periodico p ORDER BY p.avaliationArea") //busca todas as áreas sem repetir
      List<String> findDistinctAvaliationAreas();

      List<Periodico> findByAvaliationAreaIgnoreCaseAndTierIgnoreCase(String area, String tier);
}
