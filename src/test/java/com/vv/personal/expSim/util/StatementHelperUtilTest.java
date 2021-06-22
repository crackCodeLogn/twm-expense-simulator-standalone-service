package com.vv.personal.expSim.util;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vivek
 * @since 16/06/21
 */
class StatementHelperUtilTest {

    @Test
    public void testGenerateNoteForStatement() {
        assertEquals("U,testing note",
                StatementHelperUtil.generateNoteForStatement("testing note", ExpenseSimProto.TxMode.U));

        assertEquals("SAL,testing note2",
                StatementHelperUtil.generateNoteForStatement("testing note2", ExpenseSimProto.TxMode.SAL));
    }

}