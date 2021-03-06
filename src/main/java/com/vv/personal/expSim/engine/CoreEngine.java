package com.vv.personal.expSim.engine;

import com.vv.personal.expSim.util.ProtoUtil;
import com.vv.personal.expSim.util.StatementHelperUtil;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
public class CoreEngine {

    public ExpenseSimProto.StatementList initiateCompute(List<ExpenseSimProto.Bank.Builder> banks, Queue<ExpenseSimProto.Transaction> transactions, String delimiter) {
        log.info("Starting processing.");
        ExpenseSimProto.StatementList.Builder statementList = ExpenseSimProto.StatementList.newBuilder();

        Map<String, ExpenseSimProto.Bank.Builder> bankMap = new HashMap<>();
        banks.forEach(bank -> bankMap.put(bank.getCode(), bank));

        while (!transactions.isEmpty()) {
            ExpenseSimProto.Transaction transaction = transactions.poll();
            Long txDate = transaction.getDate();
            String fromBankCode = transaction.getFrom();
            String toBankCode = transaction.getTo();
            ExpenseSimProto.TxMode txMode = transaction.getMode();
            boolean fromPresent = !fromBankCode.isEmpty();
            boolean toPresent = !toBankCode.isEmpty();

            if (!fromPresent && !toPresent) {
                log.warn("Transaction empty, skipping ahead => [{}]", transaction);
                continue;
            }
            ExpenseSimProto.Bank.Builder fromBank = bankMap.getOrDefault(fromBankCode, ProtoUtil.getDefaultBankBuilder());
            ExpenseSimProto.Bank.Builder toBank = bankMap.getOrDefault(toBankCode, ProtoUtil.getDefaultBankBuilder());

            double amount = transaction.getAmt();
            String note = transaction.getNote();

            if (fromPresent) {
                fromBank.setBalance(fromBank.getBalance() - amount);
                fromBank.setDate(txDate);
            }
            if (toPresent) {
                toBank.setBalance(toBank.getBalance() + amount);
                toBank.setDate(txDate);
            }
            note = StatementHelperUtil.generateNoteForStatement(note, txMode, delimiter);
            ExpenseSimProto.BankList bankListForStatement = ProtoUtil.generateBankListForStatement(bankMap);
            statementList.addStatements(ProtoUtil.generateStatement(txDate, bankListForStatement, note, fromBankCode, toBankCode, amount));
        }
        return statementList.build();
    }
}
