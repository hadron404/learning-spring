package com.example.quickstartrest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BanTourismOrderRecordRepository extends PagingAndSortingRepository<BanTourismOrderRecord, Long> {

}
