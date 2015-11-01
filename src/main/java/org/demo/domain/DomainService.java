/**
 *
 */
package org.demo.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 30, 2015
 *
 */
@Autowired
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({
	ElementType.CONSTRUCTOR,
	ElementType.FIELD,
	ElementType.METHOD,
	ElementType.ANNOTATION_TYPE
})
public @interface DomainService {}
