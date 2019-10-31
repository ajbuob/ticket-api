package com.abuob.tickets.service;


import com.abuob.tickets.dto.InventoryDto;
import com.abuob.tickets.entity.Customer;
import com.abuob.tickets.entity.Inventory;
import com.abuob.tickets.repository.CustomerRepository;
import com.abuob.tickets.repository.InventoryRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestInventoryService {

    private InventoryService inventoryService;

    private InventoryRepository inventoryRepositoryMock = mock(InventoryRepository.class);
    private CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);

    @Before
    public void setup() {
        inventoryService = new InventoryServiceImpl(inventoryRepositoryMock, customerRepositoryMock);
    }

    @Test
    public void test_findAvailableTickets_null() {
        List<InventoryDto> resultList = inventoryService.findAvailableTickets(null);
        assertThat(resultList).isNotNull();
        assertTrue(resultList.isEmpty());
    }

    @Test
    public void test_findAvailableTickets_success() {
        Inventory inventory = new Inventory();
        inventory.setEventId(213L);
        List<Inventory> inventoryList = Lists.newArrayList(inventory);

        when(inventoryRepositoryMock.findAllByEventIdAndBuyerIsNull(anyLong())).thenReturn(inventoryList);
        List<InventoryDto> resultList = inventoryService.findAvailableTickets(213L);
        assertThat(resultList).isNotNull();
        assertTrue(!resultList.isEmpty());
    }

    @Test
    public void test_findAvailableTicketsByQuantity_null() {
        List<InventoryDto> resultList = inventoryService.findAvailableTickets(null, null);
        assertThat(resultList).isNotNull();
        assertTrue(resultList.isEmpty());
    }

    @Test
    public void test_findAvailableTicketsByQuantity_success() {
        Inventory inventory = new Inventory();
        inventory.setEventId(213L);
        List<Inventory> inventoryList = Lists.newArrayList(inventory);

        when(inventoryRepositoryMock.findAllByEventIdAndQuantityAndBuyerIsNull(anyLong(), anyInt())).thenReturn(inventoryList);
        List<InventoryDto> resultList = inventoryService.findAvailableTickets(213L, 2);
        assertThat(resultList).isNotNull();
        assertTrue(!resultList.isEmpty());
    }

    @Test
    public void test_buyInventoryItem_noInventory() {
        InventoryDto inventoryDto = inventoryService.buyInventoryItem(21334242L, 234L);
        assertThat(inventoryDto).isNull();
    }

    @Test
    public void test_buyInventoryItem_noCustomer() {
        Inventory inventory = new Inventory();
        inventory.setEventId(213L);
        List<Inventory> inventoryList = Lists.newArrayList(inventory);

        when(inventoryRepositoryMock.findAllByEventIdAndBuyerIsNull(anyLong())).thenReturn(inventoryList);
        InventoryDto inventoryDto = inventoryService.buyInventoryItem(21334242L, 234L);
        assertThat(inventoryDto).isNull();
    }

    @Test
    public void test_buyInventoryItem_success() {
        Inventory inventory = new Inventory();
        inventory.setEventId(213L);
        Optional<Inventory> optionalInventory = Optional.of(inventory);

        Customer customer = new Customer(33342L);
        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(inventoryRepositoryMock.findByIdAndBuyerIsNull(anyLong())).thenReturn(optionalInventory);
        when(customerRepositoryMock.findById(anyLong())).thenReturn(optionalCustomer);
        when(inventoryRepositoryMock.save(any(Inventory.class))).thenReturn(inventory);

        InventoryDto inventoryDto = inventoryService.buyInventoryItem(21334242L, 234L);
        assertThat(inventoryDto).isNotNull();
    }

    @Test
    public void test_sellInventoryItem_noCustomer() {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setEventId(3424L);
        inventoryDto.setQuantity(3);

        InventoryDto inventoryDtoResult = inventoryService.sellInventoryItem(inventoryDto, 3424L);
        assertThat(inventoryDtoResult).isNull();
    }

    @Test
    public void test_sellInventoryItem_success() {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setEventId(3424L);
        inventoryDto.setQuantity(3);

        Customer customer = new Customer(33342L);
        Optional<Customer> optionalCustomer = Optional.of(customer);

        Inventory inventory = new Inventory();
        inventory.setEventId(3424L);
        inventory.setQuantity(3);
        inventory.setSeller(customer);

        when(customerRepositoryMock.findById(anyLong())).thenReturn(optionalCustomer);
        when(inventoryRepositoryMock.save(any(Inventory.class))).thenReturn(inventory);

        InventoryDto inventoryDtoResult = inventoryService.sellInventoryItem(inventoryDto, 3424L);
        assertThat(inventoryDtoResult).isNotNull();
    }
}


