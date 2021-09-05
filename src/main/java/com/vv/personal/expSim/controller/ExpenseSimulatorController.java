package com.vv.personal.expSim.controller;

import com.vv.personal.expSim.config.ExpenseSimulatorConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
@RestController
@RequestMapping("/controller")
public class ExpenseSimulatorController {
    @Inject
    ExpenseSimulatorConfig expenseSimulatorConfig;

    @GetMapping("/configs")
    public String configs() {
        String data = String.format("Expense Sim config :: banks file loc: %s, transactions files loc: %s",
                expenseSimulatorConfig.banksFileLocation(), expenseSimulatorConfig.transactionsFileLocation());
        log.info(data);
        return data;
    }

}
