package demo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Fábio Jackson Mendes <fabio.mendes@navita.com.br> [May 12, 2015]
 *
 */
@Component
@ConfigurationProperties(prefix = "application")
public class ResourceGenerator {

	private int bufferSize;

	public ResourceGenerator() {
		bufferSize = 1024;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String generate() {
		List<Double> buffer = new ArrayList<>(bufferSize);
		for (int i = 0; i < bufferSize; i++) {
			buffer.add(0.0);
		}
		Random rnd = new Random(new Date().getTime());
		List<Double> list = buffer.stream()
				.map((d) -> rnd.nextDouble())
				.map(Math::sqrt)
				.sorted()
				.collect(Collectors.toList());

		return DigestUtils.sha1Hex(list.toString());
	}
}
