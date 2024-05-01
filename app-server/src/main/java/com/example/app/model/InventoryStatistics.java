package com.example.app.model;

public class InventoryStatistics {
    private int totalProducts;
    private int totalActiveProducts;
    private double totalInventoryValue;

    public InventoryStatistics() {
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getTotalActiveProducts() {
        return totalActiveProducts;
    }

    public void setTotalActiveProducts(int totalActiveProducts) {
        this.totalActiveProducts = totalActiveProducts;
    }

    public double getTotalInventoryValue() {
        return totalInventoryValue;
    }

    public void setTotalInventoryValue(double totalInventoryValue) {
        this.totalInventoryValue = totalInventoryValue;
    }
}
