package demo.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.Resource;
import demo.domain.ResourceGenerator;
import demo.domain.ResourceRepository;

/**
 * @author Fábio Jackson Mendes <fabio.mendes@navita.com.br> [May 12, 2015]
 *
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

	private ResourceGenerator generator;

	private ResourceRepository resourceRepository;

	@Autowired
	public ResourceController(ResourceGenerator generator, ResourceRepository resourceRepository) {
		this.generator = generator;
		this.resourceRepository = resourceRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Resource> getAll() {
		return resourceRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Resource create(@RequestParam String name) {
		return generator.generate(name);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Resource getOne(@PathVariable("id") Resource resource) {
		if (resource != null) {
			return resource;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable UUID id, String name) {
		generator.update(id, name);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable UUID id) {
		generator.delete(id);
	}
}
