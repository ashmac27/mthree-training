package com.sg.vendingmachine.dao;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

////Text-file specific implementation of the VendingMachineAuditDao Interface
public class VendingMachineAuditDaoImpl implements VendingMachineAuditDao {
    public static final String AUDIT_FILE = "audit.txt";
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
        }

        //Time stamp to be written in the audit file
        LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        out.println(timestamp.toString() + " | " + entry);
        out.flush();
    }
}
