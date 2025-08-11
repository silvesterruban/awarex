package com.awarex.util;

import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class ExecutionTimer {
    private static final Logger LOG = Logger.getLogger(ExecutionTimer.class.getName());
    
    public static <T> T time(String operationName, Supplier<T> operation) {
        long startTime = System.currentTimeMillis();
        try {
            return operation.get();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("%s took %d ms", operationName, duration));
        }
    }

    public static <T> ResponseEntity<T> measure(String operationName, Supplier<ResponseEntity<T>> operation) {
        long startTime = System.currentTimeMillis();
        try {
            return operation.get();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOG.info(String.format("%s took %d ms", operationName, duration));
        }
    }

    public static void time(String operationName, Runnable operation) {
        time(operationName, () -> {
            operation.run();
            return null;
        });
    }
}