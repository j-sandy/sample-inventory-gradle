package com.example.inventory.controller;

import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item) {
        // Ensure itemCode is unique before saving
        if (itemRepository.findByItemCode(item.getItemCode()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping("/{itemCode}")
    public ResponseEntity<Item> updateItem(@PathVariable String itemCode, @Valid @RequestBody Item itemDetails) {
        Optional<Item> existingItemOptional = itemRepository.findByItemCode(itemCode);
        if (existingItemOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Item existingItem = existingItemOptional.get();
        existingItem.setName(itemDetails.getName());
        existingItem.setImageUrl(itemDetails.getImageUrl());
        existingItem.setDescription(itemDetails.getDescription());
        existingItem.setQuantity(itemDetails.getQuantity());
        existingItem.setProcurementDate(itemDetails.getProcurementDate());
        existingItem.setManufacturingDate(itemDetails.getManufacturingDate());
        existingItem.setExpiryDate(itemDetails.getExpiryDate());

        Item updatedItem = itemRepository.save(existingItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{itemCode}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable String itemCode) {
        Optional<Item> itemOptional = itemRepository.findByItemCode(itemCode);
        if (itemOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        itemRepository.delete(itemOptional.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate procurementDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate,
            @RequestParam(required = false) String name) {

        if (itemCode != null) {
            return itemRepository.findByItemCode(itemCode)
                    .map(item -> ResponseEntity.ok(List.of(item)))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else if (procurementDate != null) {
            return ResponseEntity.ok(itemRepository.findByProcurementDate(procurementDate));
        } else if (expiryDate != null) {
            return ResponseEntity.ok(itemRepository.findByExpiryDate(expiryDate));
        } else if (name != null) {
            return ResponseEntity.ok(itemRepository.findByNameContainingIgnoreCase(name));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
