package com.stackly.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GeneralInterceptorAspect {

    private final Logger logger = LoggerFactory.getLogger(GeneralInterceptorAspect.class);

    
    @Pointcut("execution(* com.stackly.service.*.*(..)")
    public void serviceMethods() {}

    
    @Before("serviceMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("AOP [Before] -> Calling method: {}", joinPoint.getSignature().getName());
    }

    
    @After("serviceMethods()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("AOP [After] -> Finished method: {}", joinPoint.getSignature().getName());
    }

   
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        logger.info("AOP [AfterReturning] -> Method {} successfully returned: {}", 
                    joinPoint.getSignature().getName(), result);
    }

    
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        logger.error("AOP [AfterThrowing] -> Method {} threw exception: {}", 
                     joinPoint.getSignature().getName(), ex.getMessage());
    }

   
    @Around("serviceMethods()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // This line actually allows the service method to run
        Object result = proceedingJoinPoint.proceed(); 
        
        long endTime = System.currentTimeMillis();
        logger.info("AOP [Around] -> Execution time for {}: {} ms", 
                    proceedingJoinPoint.getSignature().getName(), (endTime - startTime));
        
        return result;
    }
}