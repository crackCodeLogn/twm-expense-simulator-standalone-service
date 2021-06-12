package com.vv.personal.expSim.reader;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vivek
 * @since 12/06/21
 */
class JsonReadBanksTest {

    @Test
    public void testBuilderRead() {
        JsonReadBanks readBanks = new JsonReadBanks("src/test/resources/banks.json");
        ExpenseSimProto.BankList bankList = readBanks.builderRead().build();

        System.out.println(bankList);
        assertEquals(2, bankList.getBanksCount());
        assertEquals("B1", bankList.getBanksList().get(0).getCode());
        assertEquals(20210612L, bankList.getBanksList().get(0).getDate());
        assertEquals(231.0, bankList.getBanksList().get(0).getBalance(), Math.pow(10, -6));
    }

}