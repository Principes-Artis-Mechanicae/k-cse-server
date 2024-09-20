package knu.univ.cse.server.global.exception.advice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestControllerAdvice
@Order
public @interface ExceptionHandlerAdvice {
	@AliasFor(annotation = Order.class, attribute = "value")
	int order() default 0;
}
