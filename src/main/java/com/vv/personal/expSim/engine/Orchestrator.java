package com.vv.personal.expSim.engine;

import com.vv.personal.expSim.config.BeanStore;
import com.vv.personal.expSim.config.ExpenseSimulatorConfig;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@ApplicationScoped
public class Orchestrator {
    private final Logger LOGGER = LoggerFactory.getLogger(Orchestrator.class);
    @Inject
    public BeanStore beanStore;
    @Inject
    ExpenseSimulatorConfig expenseSimulatorConfig;

    void onStart(@Observes StartupEvent event) {
        LOGGER.info("***Application starting up***");

        ExpenseSimProto.BankList.Builder bankBList = beanStore.JsonReadBanks().builderRead();
        ExpenseSimProto.TransactionList transactionList = beanStore.JsonReadTransactions().builderRead().build();
        if (!loadUp(bankBList.build(), transactionList)) {
            LOGGER.error("Failed to load-up completely. Exiting now!");
            Quarkus.asyncExit();
            return;
        }
        LOGGER.info("LoadUp complete!");

        ExpenseSimProto.StatementList statements = computeStatements(bankBList, transactionList);
        LOGGER.debug("Generated Statements =>\n{}", statements);
        LOGGER.info("Generated [{}] statements", statements.getStatementsCount());

        ExportToCsv exportToCsv = beanStore.ExportToCsv();
        exportToCsv.setBanks(bankBList.getBanksList());
        exportToCsv.setStatementList(statements);
        exportToCsv.export();

        LOGGER.info("*** Shutting Down! ***");
        Quarkus.asyncExit();
    }

    private boolean loadUp(ExpenseSimProto.BankList bankList, ExpenseSimProto.TransactionList transactionList) {
        return bankList.getBanksCount() > 0 && transactionList.getTransactionsCount() > 0;
    }

    public ExpenseSimProto.StatementList computeStatements(ExpenseSimProto.BankList.Builder bankBList, ExpenseSimProto.TransactionList transactionList) {
        List<ExpenseSimProto.Bank.Builder> banks = bankBList.getBanksBuilderList();
        Queue<ExpenseSimProto.Transaction> transactions = new LinkedList<>(transactionList.getTransactionsList());
        return beanStore.CoreEngine().initiateCompute(banks, transactions);
    }

}
