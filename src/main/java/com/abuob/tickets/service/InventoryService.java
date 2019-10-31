package com.abuob.tickets.service;

import com.abuob.tickets.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    List<InventoryDto> findAvailableTickets(Long eventId);

    List<InventoryDto> findAvailableTickets(Long eventId, Integer quantity);

    InventoryDto buyInventoryItem(Long inventoryId, Long buyerId);

    InventoryDto sellInventoryItem(InventoryDto inventoryDto, Long sellerId);
}
