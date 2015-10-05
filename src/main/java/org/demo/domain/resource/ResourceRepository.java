package org.demo.domain.resource;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, UUID> {

	/**
	 * @param id
	 * @return
	 */
	Optional<Resource> findById(UUID id);

}
