package com.vv.personal.expSim.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
public class FileUtil {

    public static String readFile(String fileLocation) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line.strip()).append("\n");
            }
        } catch (IOException e) {
            log.error("Could not builderRead File '{}'. ", fileLocation, e);
        }
        return result.toString();
    }
}