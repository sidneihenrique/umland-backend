package io.umland.umlandback.repository;

import io.umland.umlandback.domain.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
}
