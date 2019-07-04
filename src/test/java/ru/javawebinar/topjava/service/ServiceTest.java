package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class ServiceTest {
private static final Logger log = LoggerFactory.getLogger(ServiceTest.class);
    private static final StringBuilder classInfo =
            new StringBuilder("\nTest class profiling info.\n");

    @Rule
    public final TestRule watcher = new TestWatcher() {
        private Long startTime = null;

        @Override
        protected void starting(Description description) {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            long timeDiff = System.currentTimeMillis() - startTime;
            log.debug("test durarion is {} in milliseconds", timeDiff);
            classInfo.append(description.getMethodName())
                    .append(" - ")
                    .append(timeDiff)
                    .append(" in millis\n");
        }
    };

    @AfterClass
    public static void tearDown() throws Exception {
        log.debug(classInfo.toString());
    }
}
