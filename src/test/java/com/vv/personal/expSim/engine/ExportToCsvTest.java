package com.vv.personal.expSim.engine;

import com.vv.personal.expSim.config.BeanStore;
import com.vv.personal.expSim.reader.JsonReadBanks;
import com.vv.personal.expSim.reader.JsonReadTransactions;
import com.vv.personal.expSim.util.FileUtil;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Vivek
 * @since 12/06/21
 */
@ExtendWith(MockitoExtension.class)
public class ExportToCsvTest {

    @Mock
    BeanStore beanStore;

    @InjectMocks
    Orchestrator orchestrator = new Orchestrator();

    @Test
    public void testExport() {
        Mockito.when(beanStore.CoreEngine()).thenReturn(new CoreEngine());

        JsonReadBanks readBanks = new JsonReadBanks("src/test/resources/banks.json");
        ExpenseSimProto.BankList.Builder bankList = readBanks.builderRead();

        JsonReadTransactions readTransactions = new JsonReadTransactions("src/test/resources/transactions.json");
        ExpenseSimProto.TransactionList transactionList = readTransactions.builderRead().build();

        ExpenseSimProto.StatementList statementList = orchestrator.computeStatements(bankList, transactionList);

        ExportToCsv exportToCsv = new ExportToCsv("target");
        exportToCsv.setBanks(bankList.getBanksList());
        exportToCsv.setStatementList(statementList);
        File csv = exportToCsv.export();

        System.out.println(csv.getAbsolutePath());
        System.out.println(csv.getName());
        assertTrue(csv.exists());

        String[] lines = FileUtil.readFile(csv.getAbsolutePath()).split("\n");
        Arrays.stream(lines).forEach(System.out::println);
        assertEquals("20210612,-3880.13,501.00,B1,,4111.13,U,", lines[1]);
        assertEquals("20210615,-3880.13,441.00,B2,,10.0,U,Checking 2", lines[3]);
        csv.delete();
    }
}