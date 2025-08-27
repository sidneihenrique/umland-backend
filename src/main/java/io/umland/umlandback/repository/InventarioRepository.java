package io.umland.umlandback.repository;

import io.umland.umlandback.domain.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
