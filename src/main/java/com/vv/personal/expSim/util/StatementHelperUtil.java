package com.vv.personal.expSim.util;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;

import static com.vv.personal.expSim.constants.Constants.COMMA;

/**
 * @author Vivek
 * @since 16/06/21
 */
public class StatementHelperUtil {

    public static String generateNoteForStatement(boolean fromPresent, String fromCode, String toCode,
                                                  String note, ExpenseSimProto.TxMode txMode) {
        StringBuilder result = new StringBuilder();
        if (txMode != ExpenseSimProto.TxMode.UNRECOGNIZED) result.append(txMode);
        result.append(COMMA);
        result.append(fromPresent ? fromCode : toCode)
                .append(COMMA).append(note);
        return result.toString();
    }

}
