package geektime.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncServiceRunner implements ApplicationRunner {
    @Autowired
    private AsyncService asyncService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("call doAsync()");
        asyncService.doAsync();
        log.info("After doAsync");

        log.info("call invokeDoAsync()");
        asyncService.invokeDoAsync();
        log.info("After invokeDoAsync()");

        log.info("call invokeWithSelf()");
        asyncService.invokeWithSelf();
        log.info("After invokeWithSelf()");
    }
}
