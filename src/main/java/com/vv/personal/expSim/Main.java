package com.vv.personal.expSim;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vivek
 * @since 12/06/21
 */
@QuarkusMain
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOGGER.info("!!! Initiating code execution !!!");
        Quarkus.run(args);
    }
}
