package geektime.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataServiceRunner implements ApplicationRunner {
    @Autowired
    private DataService dataService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        demo1();
        demo2();
    }

    private void demo1() {
        dataService.insert("A");
        dataService.printAllData();
        try {
            dataService.insertAndRollback("B");
        } catch (RuntimeException e) {
            log.warn("Catch an Exception in run().");
        }
        dataService.printAllData();
        dataService.invokeMethod();
        dataService.printAllData();
    }

    private void demo2() {
        dataService.invokeWithAopContext();
        dataService.printAllData();
        dataService.invokeWithSelf();
        dataService.printAllData();
        dataService.invokeWithAC();
        dataService.printAllData();
    }
}
