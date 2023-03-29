package com.hyundai.higher.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.hyundai.higher.service.similarCos.SimilarCosServiceImpl.recogProducts(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            double timeMs = (finish - start)/1000.0;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs+"ms");
        }
    }
}