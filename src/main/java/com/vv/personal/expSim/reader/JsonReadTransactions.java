package com.vv.personal.expSim.reader;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;

/**
 * @author Vivek
 * @since 12/06/21
 */
public class JsonReadTransactions extends JsonReader<ExpenseSimProto.TransactionList.Builder> {

    public JsonReadTransactions(String jsonLocation) {
        super(jsonLocation);
    }

    @Override
    public ExpenseSimProto.TransactionList.Builder builderRead() {
        ExpenseSimProto.TransactionList.Builder builder = ExpenseSimProto.TransactionList.newBuilder();
        builderRead(builder);
        return builder;
    }

}
