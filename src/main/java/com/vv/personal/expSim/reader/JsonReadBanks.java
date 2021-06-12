package com.vv.personal.expSim.reader;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;

/**
 * @author Vivek
 * @since 12/06/21
 */
public class JsonReadBanks extends JsonReader<ExpenseSimProto.BankList.Builder> {

    public JsonReadBanks(String jsonLocation) {
        super(jsonLocation);
    }

    @Override
    public ExpenseSimProto.BankList.Builder builderRead() {
        ExpenseSimProto.BankList.Builder builder = ExpenseSimProto.BankList.newBuilder();
        builderRead(builder);
        return builder;
    }

}
