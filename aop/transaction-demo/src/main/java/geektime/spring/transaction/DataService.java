package geektime.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class DataService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataService service;
    @Autowired
    private ApplicationContext applicationContext;

    @Transactional
    public void insert(String value) {
        jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", value);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void insertAndRollback(String value) {
        log.info("insertAndRollback {}", value);
        jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", value);
        throw new RuntimeException();
    }

    public void printAllData() {
        log.info("Print all data.");
        jdbcTemplate.queryForList("SELECT * FROM FOO")
                .forEach(r -> log.info("ID: {} BAR: {}", r.get("ID"), r.get("BAR")));
    }

    public void invokeMethod() {
        try {
            insertAndRollback("C");
        } catch (RuntimeException e) {
            log.warn("Catch an Exception in invokeMethod().");
        }
    }

    public void invokeWithAopContext() {
        try {
            ((DataService) AopContext.currentProxy()).insertAndRollback("D");
        } catch (RuntimeException e) {
            log.warn("Catch an Exception in invokeWithAopContext().");
        }
    }

    public void invokeWithSelf() {
        try {
            service.insertAndRollback("E");
        } catch (RuntimeException e) {
            log.warn("Catch an Exception in invokeWithSelf().");
        }
    }

    public void invokeWithAC() {
        try {
            ((DataService) applicationContext.getBean("dataService"))
                    .insertAndRollback("F");
        } catch (RuntimeException e) {
            log.warn("Catch an Exception in invokeWithAC().");
        }
    }
}
