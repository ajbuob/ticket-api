package com.abuob.tickets.dto;

import com.google.common.base.Objects;

import java.math.BigDecimal;

public class InventoryDto {

    private Long inventoryId;

    private Long eventId;

    private Integer section;

    private Integer quantity;

    private BigDecimal price;

    private String row;

    public InventoryDto() {
    }

    public InventoryDto(Long inventoryId, Long eventId, Integer section, Integer quantity, BigDecimal price, String row) {
        this.inventoryId = inventoryId;
        this.eventId = eventId;
        this.section = section;
        this.quantity = quantity;
        this.price = price;
        this.row = row;
    }

    public InventoryDto(Long eventId, Integer section, Integer quantity, BigDecimal price, String row) {
        this.eventId = eventId;
        this.section = section;
        this.quantity = quantity;
        this.price = price;
        this.row = row;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "InventoryDto{" +
                "inventoryId=" + inventoryId +
                ", eventId=" + eventId +
                ", section=" + section +
                ", quantity=" + quantity +
                ", price=" + price +
                ", row='" + row + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryDto that = (InventoryDto) o;
        return Objects.equal(inventoryId, that.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inventoryId);
    }
}
