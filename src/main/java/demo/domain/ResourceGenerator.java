package demo.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Fábio Jackson Mendes <fabio.mendes@navita.com.br> [May 12, 2015]
 *
 */
@Component
public class ResourceGenerator {

	@Value("${application.bufferSize:25600}") // 25KB
	private int bufferSize;

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

		try (ByteArrayOutputStream objectBuffer = new ByteArrayOutputStream();
				ObjectOutputStream writer =	new ObjectOutputStream(objectBuffer)) {

			writer.writeObject(list);
			return DigestUtils.sha1Hex(objectBuffer.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
