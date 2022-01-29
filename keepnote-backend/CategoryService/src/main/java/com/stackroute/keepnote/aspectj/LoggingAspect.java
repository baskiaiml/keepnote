package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Component
@Aspect
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Category controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/*
	 * Write loggers for each of the methods of Category controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */

	@Before("execution(* com.stackroute.keepnote.controller.CategoryController.createCategory(..))")
	public void logBeforeCreateEntity(JoinPoint joinPoint) {
		logger.info("Logging before Create User Entity : " + joinPoint.getSignature().getName());
	}

	/**
	 * @param joinPoint
	 */
	@After("execution(* com.stackroute.keepnote.controller.CategoryController.createCategory(..))")
	public void logAfterCreateEntity(JoinPoint joinPoint) {

		logger.info("Logging After Create Entity : " + joinPoint.getSignature().getName());
	}

	/**
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.createCategory(..))", returning = "result")
	public void logAfterReturningCreateEntity(JoinPoint joinPoint, Object result) {

		logger.info("Logging After Returning Create Entity : " + joinPoint.getSignature().getName());
		logger.info("After Returning Result:" + result);
	}

	/**
	 * @param joinPoint
	 * @param error
	 */
	@AfterThrowing(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.createCategory(..))", throwing = "error")
	public void logAfterThrowingCreateEntity(JoinPoint joinPoint, Throwable error) {

		logger.info("Logging After Throwing Create Entity : " + joinPoint.getSignature().getName());
		logger.info("After Throwing Error:" + error);

	}

	/* For All Delete methods */
	/**
	 * @param joinPoint
	 */
	@After("execution(* com.stackroute.keepnote.controller.CategoryController.delete*(..))")
	public void logAfterDeleteEntity(JoinPoint joinPoint) {

		logger.info("Logging After Delete Entity : " + joinPoint.getSignature().getName());
	}

	/* For All Update methods */

	/**
	 * @param joinPoint
	 * @param error
	 */
	@AfterThrowing(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.updateCategory(..))", throwing = "error")
	public void logAfterThrowingUpdateEntity(JoinPoint joinPoint, Object error) {

		logger.info("Logging before Update Entity : " + joinPoint.getSignature().getName());
		logger.info("After Throwing Error:" + error);
	}

	/**
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.updateCategory(..))", returning = "result")
	public void logAfterReturningUpdateEntity(JoinPoint joinPoint, Object result) {

		logger.info("Logging After Update Entity : " + joinPoint.getSignature().getName());
		logger.info("After Throwing Error:" + result);
	}

	/* For All get and getAll methods */

	/**
	 * @param joinPoint
	 */
	@Before("execution(* com.stackroute.keepnote.controller.CategoryController.get*(..))")
	public void logBeforeGetAllEntity(JoinPoint joinPoint) {
		logger.info("logBefore() is running!");
		logger.info("Logging before Create Entity : " + joinPoint.getSignature().getName());
	}

	/**
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* com.stackroute.keepnote.controller.CategoryController.get*(..))", returning = "result")
	public void logAfterReturningGetAllEntity(JoinPoint joinPoint, Object result) {

		logger.info("Logging After Returning All Entity : " + joinPoint.getSignature().getName());
		logger.info("After Returning Result:" + result);
	}
}
