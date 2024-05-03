package com.rocs.vra.controller.item;

import com.rocs.vra.domain.item.Item;
import com.rocs.vra.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ItemService")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Optional<Item>> getItemById(@PathVariable String id) {
        return new ResponseEntity<>(this.itemService.getItemById(id), HttpStatus.OK);
    }

    @PostMapping("/item")
    public ResponseEntity<String> addItem(@RequestBody Item item){
        try {
            itemService.addItem(item);
            return new ResponseEntity<>("Item successfully added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Item cannot be added", HttpStatus.OK);
        }
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<String> updateItem(@PathVariable String id, @RequestBody Item item) {
        Item oldItem = new Item();
        Optional<Item> tempItem = itemService.getItemById(id);

        if (tempItem.isPresent()) {
            oldItem = itemService.getItemById(id).get();
        }

        if (oldItem != null) {
            oldItem.setId(item.getId());
            oldItem.setTitle(item.getTitle());
            oldItem.setGenre(item.getGenre());
            oldItem.setCopies(item.getCopies());
            oldItem.setDateCreated(item.getDateCreated());
            itemService.updateItem(oldItem);
            return new ResponseEntity<>("Item successfully updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item cannot be updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable String id) {
        try {
            itemService.deleteItemById(id);
            return new ResponseEntity<>("Item successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Item cannot be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
