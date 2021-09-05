package com.vv.personal.expSim.config;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author Vivek
 * @since 05/09/21
 */
public class DelimiterInConverter implements Converter<String> {

    @Override
    public String convert(String s) {
        return s.contains("\"") ? s.replaceAll("\"", "") : s;
    }
}