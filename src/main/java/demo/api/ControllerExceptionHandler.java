/**
 *
 */
package demo.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 4, 2015
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgument() {

	}

}
