package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Product {

    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getMaterialCostAsString(){
        return NumberFormat.getCurrencyInstance().format(costPerSquareFoot);
    }

    public String getLaborCostAsString(){
        return NumberFormat.getCurrencyInstance().format(laborCostPerSquareFoot);
    }
}
