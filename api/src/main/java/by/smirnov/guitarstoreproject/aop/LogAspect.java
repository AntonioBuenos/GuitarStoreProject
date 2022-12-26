package by.smirnov.guitarstoreproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Pointcut("within(by.smirnov.guitarstoreproject.service..*))")
    public void aroundServicePointcut() {
        //Declares pointcut
    }

    @Around("aroundServicePointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Method {} in {} started", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType());
        Object proceed = joinPoint.proceed();
        log.info("Method {} in {} finished", joinPoint.getSignature().getName(),joinPoint.getSignature().getDeclaringType());
        return proceed;
    }
}
