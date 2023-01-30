package com.whitebox.bankaccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;


@Slf4j
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BankAccountApplication.class})
@ContextConfiguration(initializers = AbstractIntegrationTest.PropertiesInitializer.class, classes = BankAccountApplication.class)
public abstract class AbstractIntegrationTest {
    @Autowired
    protected MockMvc mvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    static PostgreSQLContainer postgreSQLContainer;
//    private static final String AXON_SERVER_NAME = "axonserver";
//    private static final String MY_AXON_SERVER = "my-axon-server";
//    private static final GenericContainer AXON_SERVER = new GenericContainer("axoniq/axonserver:4.1.7")
//            .withLabel("name", MY_AXON_SERVER)
//            .withLabel("hostname", AXON_SERVER_NAME)
//            .withEnv("AXONSERVER_HOSTNAME", AXON_SERVER_NAME)
//            .withExposedPorts(8024, 8024)
//            .withExposedPorts(8124, 8124);

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres")
                .withDatabaseName("dummy_db")
                .withUsername("dummy_user")
                .withPassword("dummy_pw");
        postgreSQLContainer.start();
    }

    public static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(), "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword(), "spring.flyway.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.flyway.username=" + postgreSQLContainer.getUsername(), "spring.flyway.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());

//            AXON_SERVER.start();
//
//            Awaitility.waitAtMost(Duration.ofMinutes(10))
//                    .until(AXON_SERVER::isRunning);
//
//            TestPropertyValues.of(
//                            "axoniq.axonserver.name="+ MY_AXON_SERVER,
//                            "axoniq.axonserver.hostname=" + AXON_SERVER_NAME,
//                            "server.port=" + AXON_SERVER.getMappedPort(8024),
//                            "axoniq.axonserver.port=" + AXON_SERVER.getMappedPort(8124)
//                    )
//                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    protected String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
