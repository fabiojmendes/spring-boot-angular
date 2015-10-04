package demo.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, UUID> {

	/**
	 * @param id
	 * @return
	 */
	Optional<Resource> findById(UUID id);

}
