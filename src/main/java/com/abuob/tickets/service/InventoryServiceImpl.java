package com.abuob.tickets.service;

import com.abuob.tickets.dto.InventoryDto;
import com.abuob.tickets.entity.Customer;
import com.abuob.tickets.entity.Inventory;
import com.abuob.tickets.repository.CustomerRepository;
import com.abuob.tickets.repository.InventoryRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private InventoryRepository inventoryRepository;

    private CustomerRepository customerRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, CustomerRepository customerRepository) {
        this.inventoryRepository = inventoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<InventoryDto> findAvailableTickets(Long eventId) {
        List<InventoryDto> resultListDto = Lists.newArrayList();
        InventoryDto inventoryDto;

        logger.info("findAvailableTickets - eventId: {}", eventId);
        List<Inventory> resultList = inventoryRepository.findAllByEventIdAndBuyerIsNull(eventId);

        for (Inventory inventory : resultList) {
            inventoryDto = new InventoryDto(inventory.getId(), inventory.getEventId(),
                    inventory.getSection(), inventory.getQuantity(), inventory.getPrice(), inventory.getRow());
            resultListDto.add(inventoryDto);
        }
        logger.info("findAvailableTickets - returning result for eventId: {}", eventId);
        return resultListDto;
    }

    @Override
    public List<InventoryDto> findAvailableTickets(Long eventId, Integer quantity) {
        List<InventoryDto> resultListDto = Lists.newArrayList();
        InventoryDto inventoryDto;

        logger.info("findAvailableTickets - eventId: {} quantity: {}", eventId, quantity);
        List<Inventory> resultList = inventoryRepository.findAllByEventIdAndQuantityAndBuyerIsNull(eventId, quantity);

        for (Inventory inventory : resultList) {
            inventoryDto = new InventoryDto(inventory.getId(), inventory.getEventId(),
                    inventory.getSection(), inventory.getQuantity(), inventory.getPrice(), inventory.getRow());
            resultListDto.add(inventoryDto);
        }
        logger.info("findAvailableTickets - returning result for eventId: {} and quantity: {}", eventId, quantity);
        return resultListDto;
    }

    @Override
    public InventoryDto buyInventoryItem(Long inventoryId, Long buyerId) {
        Inventory inventoryItem;
        Customer customer;
        InventoryDto inventoryDto;
        logger.info("buyInventoryItem - inventoryId: {} buyerId: {}", inventoryId, buyerId);


        logger.info("buyInventoryItem - Checking available inventory");
        Optional<Inventory> inventoryOptional = inventoryRepository.findByIdAndBuyerIsNull(inventoryId);

        //Ensure the inventory exists on the platform
        if (!inventoryOptional.isPresent()) {
            logger.info("buyInventoryItem - inventoryId: {} is no longer for sale", inventoryId);
            return null;
        }


        inventoryItem = inventoryOptional.get();

        logger.info("buyInventoryItem - fetching customer data for buyerId: {}", buyerId);
        Optional<Customer> customerOptional = customerRepository.findById(buyerId);

        //Ensure the customer exists on the platform
        if (!customerOptional.isPresent()) {
            logger.info("buyInventoryItem - buyerId: {} doesnt exist", buyerId);
            return null;
        }

        customer = customerOptional.get();
        inventoryItem.setBuyer(customer);

        logger.info("buyInventoryItem - buying inventoryId: {} for buyerId: {}", inventoryId, buyerId);
        inventoryItem = inventoryRepository.save(inventoryItem);

        inventoryDto = new InventoryDto(inventoryItem.getId(), inventoryItem.getEventId(),
                inventoryItem.getSection(), inventoryItem.getQuantity(), inventoryItem.getPrice(), inventoryItem.getRow());

        logger.info("buyInventoryItem - returning result");
        return inventoryDto;
    }

    @Override
    public InventoryDto sellInventoryItem(InventoryDto inventoryDto, Long sellerId) {
        Customer seller;
        InventoryDto inventoryDtoResult;

        logger.info("sellInventoryItem - fetching customer data for sellerId: {}", sellerId);
        Optional<Customer> customerOptional = customerRepository.findById(sellerId);

        //Ensure the customer exists on the platform
        if (!customerOptional.isPresent()) {
            logger.info("sellInventoryItem - sellerId: {} doesnt exist", sellerId);
            return null;
        }

        seller = customerOptional.get();

        logger.info("sellInventoryItem - setting up new inventory for sellerId: {}", sellerId);
        Inventory newInventoryItem = new Inventory(inventoryDto.getEventId(), inventoryDto.getSection(),
                inventoryDto.getQuantity(), inventoryDto.getPrice(), inventoryDto.getRow(), seller);

        Inventory inventory = inventoryRepository.save(newInventoryItem);

        logger.info("sellInventoryItem - created inventoryId: {} for sellerId: {}", inventory.getId(), sellerId);
        inventoryDtoResult = new InventoryDto(inventory.getId(), inventory.getEventId(),
                inventory.getSection(), inventory.getQuantity(), inventory.getPrice(), inventory.getRow());

        return inventoryDtoResult;
    }
}
