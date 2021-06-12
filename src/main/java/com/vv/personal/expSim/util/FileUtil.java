package com.vv.personal.expSim.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Vivek
 * @since 12/06/21
 */
public class FileUtil {
    public static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static String readFile(String fileLocation) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line.strip()).append("\n");
            }
        } catch (IOException e) {
            LOGGER.error("Could not builderRead File '{}'. ", fileLocation, e);
        }
        return result.toString();
    }


}
