package dev.pgjbz;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

@SuppressWarnings("all")
public class PostgresResourceTest implements QuarkusTestResourceLifecycleManager {

    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:14-alpine"); // 1
    private PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE);

    public Map<String, String> start() {
        final String QUARKUS = "quarkus";
        final String QUARKUS_DB = "quarkus_crud";
        postgresContainer
            .withUsername(QUARKUS)
            .withPassword(QUARKUS)
            .withDatabaseName(QUARKUS_DB)
            .withExposedPorts(5432);
            // .withInitScript("products.sql");
        postgresContainer.start();

        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.username", QUARKUS);
        properties.put("quarkus.datasource.password", QUARKUS);
        properties.put("quarkus.datasource.jdbc.url",
                "jdbc:postgresql://" + postgresContainer.getHost() + ":" + postgresContainer.getFirstMappedPort()
                        + "/" + QUARKUS_DB);

        return properties;
    }

    public void stop() {
        if (Objects.nonNull(postgresContainer))
            postgresContainer.stop();
    }

}
