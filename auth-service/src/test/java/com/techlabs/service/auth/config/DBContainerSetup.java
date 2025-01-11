package com.techlabs.service.auth.config;

import org.testcontainers.containers.MySQLContainer;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class DBContainerSetup {

    private static final MySQLContainer<?> dbContainer;

    static {
        dbContainer = new MySQLContainer<>("mysql:8.0.33")
                .withDatabaseName("auth")
                .withUsername("testuser")
                .withPassword("secret");
        dbContainer.start();

        // Ensure the container is running
        await().atMost(30, TimeUnit.SECONDS).until(dbContainer::isRunning);
    }

    public static MySQLContainer<?> getDbContainer() {
        return dbContainer;
    }
}

