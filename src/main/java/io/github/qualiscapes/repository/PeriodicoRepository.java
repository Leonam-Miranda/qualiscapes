package io.github.qualiscapes.repository;

import io.github.qualiscapes.model.Periodico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PeriodicoRepository extends JpaRepository<Periodico, Long> {

      @Query("SELECT DISTINCT p.avaliationArea FROM Periodico p ORDER BY p.avaliationArea")
      List<String> findDistinctAvaliationAreas();

      @Query("SELECT p FROM Periodico p WHERE " +
              "(:area IS NULL OR p.avaliationArea = :area) AND " +
              "(:tier IS NULL OR p.tier = :tier) AND " +
              "(:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR p.issn LIKE CONCAT('%', :search, '%'))")
      List<Periodico> findByFilters(@Param("area") String area,
                                    @Param("tier") String tier,
                                    @Param("search") String search);

      List<Periodico> findByAvaliationAreaIgnoreCase(String avaliationArea);
}
