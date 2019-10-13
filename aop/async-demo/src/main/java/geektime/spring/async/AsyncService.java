package geektime.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class AsyncService {
    @Autowired
    @Lazy
    private AsyncService asyncService;

    @Async
    @Transactional
    public void doAsync() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        log.info("Thread: {}", Thread.currentThread().getName());
    }

    public void invokeDoAsync() {
        doAsync();
    }

    public void invokeWithSelf() {
        asyncService.doAsync();
    }
}
