package com.vv.personal.expSim.reader;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.util.JsonFormat;
import com.vv.personal.expSim.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
public abstract class JsonReader<T extends GeneratedMessage.Builder> {
    private final String jsonLocation;
    private final String jsonData;

    public JsonReader(String jsonLocation) {
        this.jsonLocation = jsonLocation;
        this.jsonData = FileUtil.readFile(jsonLocation);
    }

    public abstract T builderRead();

    protected void builderRead(T builder) {
        try {
            if (!StringUtils.isEmpty(jsonData)) {
                JsonFormat.parser().ignoringUnknownFields().merge(jsonData, builder);
                log.info("Read complete for '{}'", jsonLocation);
            } else {
                log.error("Empty data received!");
            }
        } catch (Exception e) {
            log.error("Failed to read BankList for the simulation. ", e);
        }
    }

}
