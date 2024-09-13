package com.aips.mio.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecutionTimeAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around("@annotation(com.aips.mio.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        logger.info("Esecuzione di {} eseguita in {} ms", joinPoint.getSignature(), executionTime);

        return proceed;
    }
}
