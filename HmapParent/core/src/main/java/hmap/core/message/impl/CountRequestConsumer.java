package hmap.core.message.impl;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.QueueMonitor;
import hmap.core.hms.system.service.IHmsActiveTraceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by machenike on 2016/10/20.
 */
@QueueMonitor(queue = "request.count")
public class CountRequestConsumer implements IMessageConsumer<String> {
    @Autowired
    private IHmsActiveTraceService hrmsUserManageService;
    private Logger logger = LoggerFactory.getLogger(CountRequestConsumer.class);
    @Override
    public void onMessage(String accountNumber, String pattern) {
        // TODO Auto-generated method stub
        if (pattern.equals("request.count")) {
            try {
                hrmsUserManageService.countRequest(accountNumber);
            } catch (Exception e) {
                logger.error("调用失败:" + accountNumber);
                e.printStackTrace();

            }
        }
    }
}
