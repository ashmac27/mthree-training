package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Objects;
/*
Order object

 */

public class Order {
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDate orderDate;


    public Order() {

    }

    public Order(int orderNumber, String customerName, String state,
                 BigDecimal taxRate, String productType, BigDecimal area,
                 BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot,
                 BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total, LocalDate orderDate) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
        this.orderDate = orderDate;
    }

    public void setOrderNumber(int orderNumber){
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public void setState(String state){
        this.state = state;
    }

    public void setTaxRate(BigDecimal taxRate){
        this.taxRate = taxRate;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot){
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public int hashCode(){
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.orderNumber);
        hash = 53 * hash + Objects.hashCode(this.customerName);
        hash = 53 * hash + Objects.hashCode(this.state);
        hash = 53 * hash + Objects.hashCode(this.taxRate);
        hash = 53 * hash + Objects.hashCode(this.productType);
        hash = 53 * hash + Objects.hashCode(this.area);
        hash = 53 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 53 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        hash = 53 * hash + Objects.hashCode(this.materialCost);
        hash = 53 * hash + Objects.hashCode(this.laborCost);
        hash = 53 * hash + Objects.hashCode(this.tax);
        hash = 53 * hash + Objects.hashCode(this.total);
        hash = 53 * hash + Objects.hashCode(this.orderDate);

        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;

        if (!Objects.equals(this.orderNumber, other.orderNumber)) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate )) {
            return false;
        }
        return true;
    }

    public String getTotalAsString(){
        return NumberFormat.getCurrencyInstance().format(total);
    }

    public void printValues() {
        System.out.println(orderNumber + "   " + customerName + "   " + state + "   " + taxRate + "   " + productType + "   " + area + "   " + costPerSquareFoot + "   " + laborCostPerSquareFoot + "   " + materialCost + "   " + laborCost + "   " + tax + "   " + total + "   " + orderDate + "   ");
    }
}