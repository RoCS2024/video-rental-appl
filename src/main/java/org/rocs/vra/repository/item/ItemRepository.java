package org.rocs.vra.repository.item;

import org.rocs.vra.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations and custom queries on Item entities.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}
