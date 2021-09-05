package com.vv.personal.expSim.engine;

import com.vv.personal.expSim.config.BeanStore;
import com.vv.personal.expSim.config.ExpenseSimulatorConfig;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
@ApplicationScoped
public class Orchestrator {
    @Inject
    public BeanStore beanStore;
    @Inject
    ExpenseSimulatorConfig expenseSimulatorConfig;

    void onStart(@Observes StartupEvent event) {
        log.info("***Application starting up***");

        ExpenseSimProto.BankList.Builder bankBList = beanStore.JsonReadBanks().builderRead();
        ExpenseSimProto.TransactionList transactionList = beanStore.JsonReadTransactions().builderRead().build();
        if (!loadUp(bankBList.build(), transactionList)) {
            log.error("Failed to load-up completely. Exiting now!");
            Quarkus.asyncExit();
            return;
        }
        log.info("LoadUp complete!");

        ExpenseSimProto.StatementList statements = computeStatements(bankBList, transactionList);
        log.debug("Generated Statements =>\n{}", statements);
        log.info("Generated [{}] statements", statements.getStatementsCount());

        ExportToCsv exportToCsv = beanStore.ExportToCsv();
        exportToCsv.setBanks(bankBList.getBanksList());
        exportToCsv.setStatementList(statements);
        exportToCsv.export();

        log.info("*** Shutting Down! ***");
        Quarkus.asyncExit();
    }

    private boolean loadUp(ExpenseSimProto.BankList bankList, ExpenseSimProto.TransactionList transactionList) {
        return bankList.getBanksCount() > 0 && transactionList.getTransactionsCount() > 0;
    }

    public ExpenseSimProto.StatementList computeStatements(ExpenseSimProto.BankList.Builder bankBList, ExpenseSimProto.TransactionList transactionList) {
        List<ExpenseSimProto.Bank.Builder> banks = bankBList.getBanksBuilderList();
        Queue<ExpenseSimProto.Transaction> transactions = new LinkedList<>(transactionList.getTransactionsList());
        return beanStore.CoreEngine().initiateCompute(banks, transactions, expenseSimulatorConfig.simulatorCsvDelimiter());
    }

}
