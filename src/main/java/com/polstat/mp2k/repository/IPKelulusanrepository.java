package com.polstat.mp2k.repository;

import com.polstat.mp2k.entity.IP_Kelulusan;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ipKelulusan", path = "ipKelulusan")
public interface IPKelulusanrepository extends PagingAndSortingRepository<IP_Kelulusan, Long>, CrudRepository<IP_Kelulusan, Long>{
    List<IP_Kelulusan> findByUserId(Long userId);
}
