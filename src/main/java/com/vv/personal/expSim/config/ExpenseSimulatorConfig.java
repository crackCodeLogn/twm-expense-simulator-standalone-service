package com.vv.personal.expSim.config;

import io.quarkus.arc.config.ConfigProperties;

/**
 * @author Vivek
 * @since 12/06/21
 */
@ConfigProperties
public class ExpenseSimulatorConfig {

    private String banksFileLocation;
    private String transactionsFileLocation;
    private String simulatorCsvFileLocation;

    public String getBanksFileLocation() {
        return banksFileLocation;
    }

    public void setBanksFileLocation(String banksFileLocation) {
        this.banksFileLocation = banksFileLocation;
    }

    public String getTransactionsFileLocation() {
        return transactionsFileLocation;
    }

    public void setTransactionsFileLocation(String transactionsFileLocation) {
        this.transactionsFileLocation = transactionsFileLocation;
    }

    public String getSimulatorCsvFileLocation() {
        return simulatorCsvFileLocation;
    }

    public void setSimulatorCsvFileLocation(String simulatorCsvFileLocation) {
        this.simulatorCsvFileLocation = simulatorCsvFileLocation;
    }
}
