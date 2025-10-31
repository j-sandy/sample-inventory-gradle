package com.example.inventory.repository;

import com.example.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemCode(String itemCode);
    List<Item> findByProcurementDate(LocalDate procurementDate);
    List<Item> findByExpiryDate(LocalDate expiryDate);
    List<Item> findByNameContainingIgnoreCase(String name);
}
