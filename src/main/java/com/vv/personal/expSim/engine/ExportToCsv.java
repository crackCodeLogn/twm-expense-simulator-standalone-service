package com.vv.personal.expSim.engine;

import com.google.common.collect.Lists;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
public class ExportToCsv {
    private final File csvLandingFile;
    private final String delimiter;

    private List<ExpenseSimProto.Bank> banks;
    private ExpenseSimProto.StatementList statementList;

    public ExportToCsv(String csvLandingFileLocation, String delimiter) {
        this.csvLandingFile = new File(String.format("%s/SimulatorResult-%d.csv", csvLandingFileLocation, System.currentTimeMillis()));
        this.delimiter = delimiter;
    }

    public File export() {
        List<String> dataToWrite = generateDataToWrite();
        if (writeCsv(dataToWrite)) {
            log.info("CSV generation completed at: {}", csvLandingFile.getAbsolutePath());
            return csvLandingFile;
        }
        log.error("CSV generation failed!");
        return null;
    }

    private List<String> generateDataToWrite() {
        List<String> dataLines = Lists.newArrayList();
        List<String> bankCodes = banks.stream().map(ExpenseSimProto.Bank::getCode).collect(Collectors.toList());

        //populating header row
        StringBuilder header = new StringBuilder("Date").append(delimiter);
        String columns = StringUtils.join(bankCodes, delimiter);
        header.append(columns);
        header.append(delimiter).append("From").append(delimiter).append("To").append(delimiter)
                .append("Amount").append(delimiter).append("Mode").append(delimiter).append("Note");
        dataLines.add(header.toString().strip());

        //populating lines
        statementList.getStatementsList().forEach(statement -> {
            StringBuilder line = new StringBuilder(String.valueOf(statement.getDate())).append(delimiter);
            Map<String, ExpenseSimProto.Bank> bankMap = statement.getBankMapMap();
            line.append(
                    StringUtils.join(bankCodes.stream().map(code ->
                                    String.format("%.2f", bankMap.get(code).getBalance()))
                            .collect(Collectors.toList()), delimiter)
            );
            line.append(delimiter).append(statement.getFrom());
            line.append(delimiter).append(statement.getTo());
            line.append(delimiter).append(statement.getAmt());
            line.append(delimiter).append(statement.getNote());
            dataLines.add(line.toString().strip());
        });

        return dataLines;
    }

    private boolean writeCsv(List<String> dataLines) {
        try (FileWriter fileWriter = new FileWriter(csvLandingFile)) {
            for (String line : dataLines) {
                try {
                    fileWriter.write(line.strip() + "\n");
                } catch (IOException ioException) {
                    log.error("Error while writing data to CSV. ", ioException);
                    return false;
                }
            }
            return true;
        } catch (IOException ioException) {
            log.error("Failed to write data to CSV. ", ioException);
        }
        return false;
    }

    public void setBanks(List<ExpenseSimProto.Bank> banks) {
        this.banks = banks;
    }

    public void setStatementList(ExpenseSimProto.StatementList statementList) {
        this.statementList = statementList;
    }
}
