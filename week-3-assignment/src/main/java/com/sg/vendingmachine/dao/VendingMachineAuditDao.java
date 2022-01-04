package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingMachine;

public interface VendingMachineAuditDao {
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
