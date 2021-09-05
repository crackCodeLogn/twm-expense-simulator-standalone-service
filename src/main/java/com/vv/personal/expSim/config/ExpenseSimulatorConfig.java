package com.vv.personal.expSim.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;

/**
 * @author Vivek
 * @since 12/06/21
 */
@ConfigMapping(prefix = "expense-simulator")
public interface ExpenseSimulatorConfig {

    String banksFileLocation();

    String transactionsFileLocation();

    String simulatorCsvFileLocation();

    @WithConverter(DelimiterInConverter.class)
    String simulatorCsvDelimiter();
}