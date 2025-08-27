package io.umland.umlandback.repository;

import io.umland.umlandback.domain.DiagramaIdeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagramaIdealRepository extends JpaRepository<DiagramaIdeal, Long> {
    List<DiagramaIdeal> findByFaseId(Long faseId);
}
