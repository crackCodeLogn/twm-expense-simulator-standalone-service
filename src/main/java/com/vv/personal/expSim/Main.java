package com.vv.personal.expSim;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Vivek
 * @since 12/06/21
 */
@Slf4j
@QuarkusMain
public class Main {
    public static void main(String... args) {
        log.info("!!! Initiating code execution !!!");
        Quarkus.run(args);
    }
}
