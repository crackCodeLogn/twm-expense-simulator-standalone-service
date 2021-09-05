package com.vv.personal.expSim.util;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;

/**
 * @author Vivek
 * @since 16/06/21
 */
public class StatementHelperUtil {

    public static String generateNoteForStatement(String note, ExpenseSimProto.TxMode txMode, String delimiter) {
        StringBuilder result = new StringBuilder();
        if (txMode != ExpenseSimProto.TxMode.UNRECOGNIZED) result.append(txMode);
        result.append(delimiter);
        result//.append(fromPresent ? fromCode : toCode).append(delimiter)
                .append(note);
        return result.toString();
    }

}
