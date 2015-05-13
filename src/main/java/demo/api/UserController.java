package demo.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fábio Jackson Mendes <fabio.mendes@navita.com.br> [May 12, 2015]
 *
 */
@RestController
public class UserController {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

}
