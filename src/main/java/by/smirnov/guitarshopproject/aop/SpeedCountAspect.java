package by.smirnov.guitarshopproject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class SpeedCountAspect {
    @Pointcut("execution(* by.smirnov.guitarshopproject.repository.guitar.HibernateGuitarRepo.*(..))")
    public void aroundGuitarRepoPointcut() {
    }

    @Around("aroundGuitarRepoPointcut()")
    public Object guitarMethodSpeedController(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String methodPackage = signature.getDeclaringTypeName();
        String method = signature.getName();

        StopWatch sw = new StopWatch();
        sw.start();

        Object proceed = joinPoint.proceed();

        sw.stop();
        System.out.println(String.format("Время выполнения метода %s пакета %s: %n%s",
                method, methodPackage, sw.prettyPrint()));

        return proceed;
    }

    @Pointcut("execution(* by.smirnov.guitarshopproject.repository.user.HibernateUserRepo.*(..))")
    public void aroundUserRepoPointcut() {
    }

    @Around("aroundUserRepoPointcut()")
    public Object userMethodSpeedController(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String methodPackage = signature.getDeclaringTypeName();
        String method = signature.getName();

        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Время выполнения метода %s пакета %s: %d ms.",
                method, methodPackage, (endTime - startTime)));

        return proceed;
    }
}
