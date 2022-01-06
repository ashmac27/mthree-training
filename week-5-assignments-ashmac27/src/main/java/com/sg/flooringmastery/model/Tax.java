package com.sg.flooringmastery.model;

import java.math.BigDecimal;

public class Tax {

    private String stateAbbrev;
    private String stateName;
    private BigDecimal taxRate;

    public String getStateAbbrev() {
        return stateAbbrev;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbrev = stateAbbreviation;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
