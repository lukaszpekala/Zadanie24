package com.example.fbtransactions;

public enum TransactionType {
    CREDIT("wpływ"),
    CHARGE("wydatek");


    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}