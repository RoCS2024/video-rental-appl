package org.rocs.vra.controller.item;

import org.rocs.vra.domain.item.Item;
import org.rocs.vra.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Item>> getItemById(@PathVariable Long id) {
        return new ResponseEntity<>(this.itemService.getItemById(id), HttpStatus.OK);
    }

    @PostMapping("/save-item")
    public ResponseEntity<String> addItem(@RequestBody Item item){
        try {
            item.setDateCreated(new Date());
            itemService.addItem(item);
            return new ResponseEntity<>("Item successfully added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Item cannot be added", HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable Long id) {
        try {
            itemService.deleteItemById(id);
            return new ResponseEntity<>("Item successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Item cannot be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
