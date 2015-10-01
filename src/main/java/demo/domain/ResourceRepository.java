package demo.domain;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, UUID> {

}
