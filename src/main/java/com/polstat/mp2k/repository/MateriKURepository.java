
package com.polstat.mp2k.repository;

import com.polstat.mp2k.entity.MateriKU;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "materiKU", path = "materiKU")
public interface MateriKURepository extends PagingAndSortingRepository<MateriKU, Long>, CrudRepository<MateriKU, Long>{
    Optional<MateriKU> findByName(String name);
}
