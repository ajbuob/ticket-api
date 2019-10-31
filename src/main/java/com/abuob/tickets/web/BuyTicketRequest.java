package com.abuob.tickets.web;

public class BuyTicketRequest {

    private Long buyerId;

    private Long inventoryId;

    public BuyTicketRequest() {
    }

    public BuyTicketRequest(Long buyerId, Long inventoryId) {
        this.buyerId = buyerId;
        this.inventoryId = inventoryId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public String toString() {
        return "BuyTicketRequest{" +
                "buyerId=" + buyerId +
                ", inventoryId=" + inventoryId +
                '}';
    }
}
