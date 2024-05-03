package com.rocs.vra.service.item;

import com.rocs.vra.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> getAllItems();
    Optional<Item> getItemById(String id);
    Item addItem(Item item);
    Item updateItem(Item item);
    void deleteItemById(String id);
}
