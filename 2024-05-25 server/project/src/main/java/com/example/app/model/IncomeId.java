package com.example.app.model;

import java.io.Serializable;

public class IncomeId implements Serializable {

    private User user;
    private Long shipmentId;

    public IncomeId() {
    }

    public IncomeId(User user, Long shipmentId) {
        this.user = user;
        this.shipmentId = shipmentId;
    }

    // equals and hashCode methods are required

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeId incomeId = (IncomeId) o;

        if (!user.equals(incomeId.user)) return false;
        return shipmentId.equals(incomeId.shipmentId);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + shipmentId.hashCode();
        return result;
    }
}
