package com.abuob.tickets.repository;

import com.abuob.tickets.entity.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    Optional<Inventory> findById(Long id);

    Optional<Inventory> findByIdAndBuyerIsNull(Long Id);

    List<Inventory> findAll();

    List<Inventory> findAllByEventIdAndBuyerIsNull(Long eventId);

    List<Inventory> findAllByEventIdAndQuantityAndBuyerIsNull(Long eventId, Integer quantity);

    Inventory save(Inventory inventory);
}
