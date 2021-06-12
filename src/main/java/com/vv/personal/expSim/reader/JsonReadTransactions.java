package com.vv.personal.expSim.reader;

import com.google.common.collect.Lists;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<ExpenseSimProto.Transaction.Builder> unsortedRawInput = Lists.newArrayList(builder.getTransactionsBuilderList());
        unsortedRawInput.sort(Comparator.comparing(ExpenseSimProto.Transaction.Builder::getDate));
        builder.clear();
        builder.addAllTransactions(unsortedRawInput.stream().map(ExpenseSimProto.Transaction.Builder::build).collect(Collectors.toList()));
        return builder;
    }

}
