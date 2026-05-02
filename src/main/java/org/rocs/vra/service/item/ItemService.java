package org.rocs.vra.service.item;

import org.rocs.vra.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> getAllItems();
    Optional<Item> getItemById(Long id);
    Item addItem(Item item);
    Item updateItem(Item item);
    void deleteItemById(Long id);
}
