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
        assertEquals("U,B3,testing note",
                StatementHelperUtil.generateNoteForStatement(true, "B3", "B5", "testing note", ExpenseSimProto.TxMode.U));

        assertEquals("SAL,B5,testing note2",
                StatementHelperUtil.generateNoteForStatement(false, "B3", "B5", "testing note2", ExpenseSimProto.TxMode.SAL));
    }

}