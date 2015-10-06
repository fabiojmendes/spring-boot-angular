package org.demo.rest;

import java.util.Optional;
import java.util.UUID;

import org.demo.domain.resource.Resource;
import org.demo.domain.resource.ResourceGenerator;
import org.demo.domain.resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fábio Jackson Mendes <fabio.mendes@navita.com.br> [May 12, 2015]
 *
 */
@RestController
@RequestMapping("/api/resource")
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
		return resourceRepository.findAll(new Sort("name"));
	}

	@RequestMapping(method = RequestMethod.POST)
	public Resource create(@RequestBody ResourceForm form) {
		return generator.generate(form.name);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Resource getOne(@PathVariable("id") Optional<Resource> resource) {
		return resource.orElseThrow(IllegalArgumentException::new);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable UUID id, @RequestBody ResourceForm form) {
		generator.update(id, form.name);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable UUID id) {
		generator.delete(id);
	}
}
