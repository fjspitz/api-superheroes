package ar.com.fjs.api.superheroes.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAdvice {
	
	@Around("@annotation(ar.com.fjs.api.superheroes.aop.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endtime = System.currentTimeMillis();
        
        log.info("Class name: {}. Method name: {}. Time taken for execution was: {}ms", 
        		point.getSignature().getDeclaringTypeName(), 
        		point.getSignature().getName(), 
        		(endtime - startTime));
        
        return object;
    }
}
