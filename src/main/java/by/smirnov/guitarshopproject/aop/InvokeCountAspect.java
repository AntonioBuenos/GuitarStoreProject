package by.smirnov.guitarshopproject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class InvokeCountAspect {

    private int create = 0;
    private int findById = 0;
    private int findOne = 0;
    private int findAll = 0;
    private int update = 0;
    private int delete = 0;
    private int showDeletedUsers = 0;

    @Pointcut("execution(* by.smirnov.guitarshopproject.repository.user.HibernateUserRepo.*(..))")
    public void aroundUserRepoPointcut() {
    }

    @Around("aroundUserRepoPointcut()")
    public Object userMethodInvokedCounter(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String methodPackage = signature.getDeclaringTypeName();
        String method = signature.getName();

        System.out.print(String.format("Метод %s вызван раз: ", method));
        switch (method){
            case "create" -> System.out.println(++create);
            case "findById" -> System.out.println(++findById);
            case "findOne" -> System.out.println(++findOne);
            case "findAll" -> System.out.println(++findAll);
            case "update" -> System.out.println(++update);
            case "delete" -> System.out.println(++delete);
            case "showDeletedUsers" -> System.out.println(++showDeletedUsers);
        }

        Object proceed = joinPoint.proceed();
        return proceed;
    }
}
