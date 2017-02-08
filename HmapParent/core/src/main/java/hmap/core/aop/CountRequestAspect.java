package hmap.core.aop;

import com.hand.hap.message.IMessagePublisher;
import hmap.core.security.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by machenike on 2016/10/20.
 */
@Aspect
@Component
public class CountRequestAspect {
    @Autowired
    IMessagePublisher messagePublisher;
    private static final Logger logger = LoggerFactory.getLogger(CountRequestAspect.class);

    @Pointcut("execution(* hmap.core.hms.controllers.*.*(..))")
    public void countAspect() {}

    @Before("countAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            logger.debug("CountRequestAspect:doBefore");
            String userName = SecurityUtils.getCurrentUserLogin();
            //用户角色
            if (userName != null) {
                messagePublisher.rPush("request.count", userName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
