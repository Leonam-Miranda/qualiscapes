package io.github.qualiscapes.repository;

import io.github.qualiscapes.model.Periodico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeriodicoRepository extends JpaRepository<Periodico, Long> {

      @Query("SELECT DISTINCT p.avaliationArea FROM Periodico p ORDER BY p.avaliationArea")
      List<String> findDistinctAvaliationAreas();

      @Query("""
        SELECT p
        FROM Periodico p
        WHERE (:area IS NULL OR p.avaliationArea = :area)
          AND (:search IS NULL
               OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))
               OR p.issn LIKE CONCAT('%', :search, '%'))
          AND (:applyTierFilter = false OR p.tier IN :tiers)
        """)
      Page<Periodico> findByFilters(
              @Param("area") String area,
              @Param("tiers") List<String> tiers,
              @Param("applyTierFilter") boolean applyTierFilter,
              @Param("search") String search,
              Pageable pageable
      );
}