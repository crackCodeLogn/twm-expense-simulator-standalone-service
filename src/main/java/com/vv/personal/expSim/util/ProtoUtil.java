package com.vv.personal.expSim.util;

import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vivek
 * @since 12/06/21
 */
public class ProtoUtil {

    public static ExpenseSimProto.Statement generateStatement(Long date, ExpenseSimProto.BankList bankList, String note,
                                                              String fromBankCode, String toBankCode, Double amount) {
        Map<String, ExpenseSimProto.Bank> bankMap = new HashMap<>();
        bankList.getBanksList().forEach(bank -> bankMap.put(bank.getCode(), bank));
        return ExpenseSimProto.Statement.newBuilder()
                .setDate(date)
                .putAllBankMap(bankMap)
                .setNote(note)
                .setFrom(fromBankCode)
                .setTo(toBankCode)
                .setAmt(amount)
                .build();
    }

    public static ExpenseSimProto.Bank.Builder getDefaultBankBuilder() {
        return ExpenseSimProto.Bank.newBuilder();
    }

    public static ExpenseSimProto.Bank generateBankDetail(ExpenseSimProto.Bank.Builder bankBuilder) {
        return ExpenseSimProto.Bank.newBuilder()
                .setCode(bankBuilder.getCode())
                .setDate(bankBuilder.getDate())
                .setBalance(bankBuilder.getBalance())
                .build();
    }

    public static ExpenseSimProto.BankList generateBankListForStatement(Map<String, ExpenseSimProto.Bank.Builder> bankMap) {
        ExpenseSimProto.BankList.Builder builder = ExpenseSimProto.BankList.newBuilder();
        bankMap.forEach((code, bank) -> builder.addBanks(generateBankDetail(bank)));
        return builder.build();
    }
}
