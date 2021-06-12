package com.vv.personal.expSim.engine;

import com.google.common.collect.Lists;
import com.vv.personal.twm.artifactory.generated.expSim.ExpenseSimProto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vv.personal.expSim.constants.Constants.COMMA;

/**
 * @author Vivek
 * @since 12/06/21
 */
public class ExportToCsv {
    private final Logger LOGGER = LoggerFactory.getLogger(ExportToCsv.class);

    private final File csvLandingFile;
    private List<ExpenseSimProto.Bank> banks;
    private ExpenseSimProto.StatementList statementList;

    public ExportToCsv(String csvLandingFileLocation) {
        this.csvLandingFile = new File(String.format("%s/SimulatorResult-%d.csv", csvLandingFileLocation, System.currentTimeMillis()));
    }

    public File export() {
        List<String> dataToWrite = generateDataToWrite();
        if (writeCsv(dataToWrite)) {
            LOGGER.info("CSV generation completed at: {}", csvLandingFile.getAbsolutePath());
            return csvLandingFile;
        }
        LOGGER.error("CSV generation failed!");
        return null;
    }

    private List<String> generateDataToWrite() {
        List<String> dataLines = Lists.newArrayList();
        List<String> bankCodes = banks.stream().map(ExpenseSimProto.Bank::getCode).collect(Collectors.toList());

        //populating header row
        StringBuilder header = new StringBuilder("Date").append(COMMA);
        String columns = StringUtils.join(bankCodes, COMMA);
        header.append(columns);
        dataLines.add(header.toString().strip());

        //populating lines
        statementList.getStatementsList().forEach(statement -> {
            StringBuilder line = new StringBuilder(String.valueOf(statement.getDate())).append(COMMA);
            Map<String, ExpenseSimProto.Bank> bankMap = statement.getBankMapMap();
            line.append(
                    StringUtils.join(bankCodes.stream().map(code ->
                            String.format("%.2f", bankMap.get(code).getBalance()))
                            .collect(Collectors.toList()), COMMA)
            );
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
                    LOGGER.error("Error while writing data to CSV. ", ioException);
                    return false;
                }
            }
            return true;
        } catch (IOException ioException) {
            LOGGER.error("Failed to write data to CSV. ", ioException);
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
