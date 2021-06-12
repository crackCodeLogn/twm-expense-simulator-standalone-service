package com.vv.personal.expSim.controller;

import com.vv.personal.expSim.config.ExpenseSimulatorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Vivek
 * @since 12/06/21
 */
@RestController
@RequestMapping("/controller")
public class ExpenseSimulatorController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExpenseSimulatorController.class);

    @Inject
    ExpenseSimulatorConfig expenseSimulatorConfig;

    @GetMapping("/configs")
    public String configs() {
        String data = String.format("Expense Sim config :: banks file loc: %s, transactions files loc: %s",
                expenseSimulatorConfig.getBanksFileLocation(), expenseSimulatorConfig.getTransactionsFileLocation());
        LOGGER.info(data);
        return data;
    }

}
