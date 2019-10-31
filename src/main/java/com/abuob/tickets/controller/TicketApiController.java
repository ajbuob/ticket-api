package com.abuob.tickets.controller;

import com.abuob.tickets.dto.InventoryDto;
import com.abuob.tickets.service.InventoryService;
import com.abuob.tickets.web.BuyTicketRequest;
import com.abuob.tickets.web.SellTicketRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TicketApiController {

    private static final Logger logger = LoggerFactory.getLogger(TicketApiController.class);

    private static final String SEARCH_TYPE_PRICE = "price";
    private static final String SEARCH_TYPE_SEAT = "seat";


    private InventoryService inventoryService;

    @Autowired
    public TicketApiController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //Retrieves all the available tickets for a given event
    @RequestMapping(value = "/inventory/findAllAvailableByEvent/{eventId}", method = RequestMethod.GET)
    public ResponseEntity<List<InventoryDto>> findAllTickets(@PathVariable("eventId") Long eventId) {

        //Basic Validation of the request
        if (Objects.isNull(eventId) || eventId <= 0) {
            logger.info("sellTicket - detected bad input data");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        logger.info("findAllTickets - looking for tickets for eventId: {}", eventId);
       // List<InventoryDto> eventInventory =

        List<InventoryDto> eventInventory =  inventoryService.findAvailableTickets(eventId);
        return new ResponseEntity<>(eventInventory, HttpStatus.OK);

    }

    //Retrieves all the available tickets for a given event and quantity.
    // Optional searchType query parameter to find "best" ticket as defined by the parameter
    @RequestMapping(value = "/inventory/findBestAvailableByQuantity/{eventId}/{quantity}", method = RequestMethod.GET)
    public ResponseEntity<List<InventoryDto>> findBestAvailableTickets(@PathVariable("eventId") Long eventId,
                                                                       @PathVariable("quantity") Integer quantity,
                                                                       @RequestParam(value = "searchType", required = false) String searchType) {

        //Basic Validation of the request
        if (Objects.isNull(eventId) || eventId <= 0 || Objects.isNull(quantity) || quantity <= 0) {
            logger.info("findBestAvailableTickets - detected bad input data");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<InventoryDto> eventInventory = inventoryService.findAvailableTickets(eventId, quantity);

        if (!Objects.isNull(searchType) && searchType.equals(SEARCH_TYPE_PRICE)) {
            logger.info("findBestAvailableTickets - looking for quantity: {} for eventId: {} with best price", quantity, eventId);
            //Equivalent of select * from inventory where quantity=<qty> and buyer_id is null and event_id=<id> order by price
            Collections.sort(eventInventory, Comparator.comparing(InventoryDto::getPrice));
            return new ResponseEntity<>(eventInventory.subList(0, 1), HttpStatus.OK);
        }

        if (!Objects.isNull(searchType) && searchType.equals(SEARCH_TYPE_SEAT)) {
            logger.info("findBestAvailableTickets - looking for quantity: {} for eventId: {} with closest seat", quantity, eventId);
            //Equivalent of select * from inventory where quantity=<qty> and buyer_id is null and event_id=<id> order by sect, row
            Collections.sort(eventInventory, Comparator.comparingInt(InventoryDto::getSection).thenComparing(InventoryDto::getRow));
            return new ResponseEntity<>(eventInventory.subList(0, 1), HttpStatus.OK);
        }

        logger.info("findBestAvailableTickets - looking for quantity: {} for eventId: {}", quantity, eventId);
        return new ResponseEntity<>(eventInventory, HttpStatus.OK);
    }

    //Endpoint for customer to advertise their tickets in the application
    @RequestMapping(value = "/inventory/sellTicket",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDto> sellTicket(@RequestBody SellTicketRequest sellTicketRequest) {

        Long sellerId = sellTicketRequest.getSellerId();
        Long eventId = sellTicketRequest.getEventId();
        Integer section = sellTicketRequest.getSection();
        Integer quantity = sellTicketRequest.getQuantity();
        BigDecimal price = sellTicketRequest.getPrice();
        String row = sellTicketRequest.getRow();

        //Basic Validation of the request
        if (Objects.isNull(sellerId) || Objects.isNull(eventId) || Objects.isNull(section) || Objects.isNull(quantity) ||
                Objects.isNull(price) || StringUtils.isEmpty(row)) {
            logger.info("sellTicket - detected bad input data");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        logger.info("sellTicket - customerId: {} trying to list for eventId: {}", sellerId, eventId);
        InventoryDto inventoryDto = new InventoryDto(eventId, section, quantity, price, row);
        inventoryDto = inventoryService.sellInventoryItem(inventoryDto, sellerId);
        return new ResponseEntity<>(inventoryDto, HttpStatus.CREATED);


    }

    //Endpoint for customer to buy available tickets in the application
    @RequestMapping(value = "/inventory/buyTicket", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryDto> buyTicket(@RequestBody BuyTicketRequest buyTicketRequest) {

        Long inventoryId = buyTicketRequest.getInventoryId();
        Long buyerId = buyTicketRequest.getBuyerId();

        //Basic Validation of the request
        if (Objects.isNull(inventoryId) || Objects.isNull(buyerId)) {
            logger.info("buyTicket - detected bad input data");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        logger.info("buyTicket - customerId: {} trying to buy inventoryId: {}", buyerId, inventoryId);
        InventoryDto inventoryDto = inventoryService.buyInventoryItem(inventoryId, buyerId);
        return new ResponseEntity<>(inventoryDto, HttpStatus.CREATED);
    }
}
