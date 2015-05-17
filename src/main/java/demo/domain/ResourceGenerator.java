package demo.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Fábio Jackson Mendes <fabio.mendes@navita.com.br> [May 12, 2015]
 *
 */
@Component
public class ResourceGenerator {

	@Value("${application.bufferSize:10240}")
	private int bufferSize;

	public String generate() {
		byte[] buffer = new byte[bufferSize];
		Random rnd = new Random(new Date().getTime());
		rnd.nextBytes(buffer);
		Arrays.sort(buffer);
		return DigestUtils.sha1Hex(buffer);
	}

}
