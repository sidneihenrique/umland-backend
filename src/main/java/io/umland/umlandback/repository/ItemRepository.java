package io.umland.umlandback.repository;

import io.umland.umlandback.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
