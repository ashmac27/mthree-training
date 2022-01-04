package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.VendingMachine;

public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {

    VendingMachine onlyOne;

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException{
        //does nothing
    }
}
