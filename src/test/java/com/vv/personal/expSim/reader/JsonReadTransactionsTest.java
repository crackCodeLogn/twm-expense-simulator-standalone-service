package com.vv.personal.expSim.reader;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vivek
 * @since 12/06/21
 */
class JsonReadTransactionsTest {

    @Test
    public void testBuilderRead() {
        JsonReadTransactions readTransactions = new JsonReadTransactions("src/test/resources/transactions.json");
        ExpenseSimProto.TransactionList transactionList = readTransactions.builderRead().build();

        System.out.println(transactionList);
        assertEquals(3, transactionList.getTransactionsCount());
        assertEquals("B1", transactionList.getTransactionsList().get(0).getFrom());
        assertEquals("", transactionList.getTransactionsList().get(0).getTo());
        assertEquals(20210612L, transactionList.getTransactionsList().get(0).getDate());
        assertEquals(4111.13, transactionList.getTransactionsList().get(0).getAmt(), Math.pow(10, -6));
    }
}