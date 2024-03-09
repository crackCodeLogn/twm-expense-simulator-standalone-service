package com.vv.personal.expSim.config;

import com.vv.personal.expSim.engine.CoreEngine;
import com.vv.personal.expSim.engine.ExportToCsv;
import com.vv.personal.expSim.reader.JsonReadBanks;
import com.vv.personal.expSim.reader.JsonReadTransactions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Configuration
public class BeanStore {

    @Inject
    ExpenseSimulatorConfig expenseSimulatorConfig;

    @Bean
    public JsonReadBanks JsonReadBanks() {
        return new JsonReadBanks(expenseSimulatorConfig.banksFileLocation());
    }

    @Bean
    public JsonReadTransactions JsonReadTransactions() {
        return new JsonReadTransactions(expenseSimulatorConfig.transactionsFileLocation());
    }

    @Bean
    public CoreEngine CoreEngine() {
        return new CoreEngine();
    }

    @Bean
    public ExportToCsv ExportToCsv() {
        return new ExportToCsv(expenseSimulatorConfig.simulatorCsvFileLocation(),
                expenseSimulatorConfig.simulatorCsvCopyFileLocation(),
                expenseSimulatorConfig.simulatorCsvDelimiter());
    }
}