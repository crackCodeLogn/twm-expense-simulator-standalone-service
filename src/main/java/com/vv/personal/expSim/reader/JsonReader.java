package com.vv.personal.expSim.reader;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.util.JsonFormat;
import com.vv.personal.expSim.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vivek
 * @since 12/06/21
 */
public abstract class JsonReader<T extends GeneratedMessageV3.Builder> {
    private final Logger LOGGER = LoggerFactory.getLogger(JsonReader.class);
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
                LOGGER.info("Read complete for '{}'", jsonLocation);
            } else {
                LOGGER.error("Empty data received!");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to read BankList for the simulation. ", e);
        }
    }

}
