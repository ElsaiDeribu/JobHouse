package com.jobhouse.finder.Repositories;

import com.jobhouse.finder.Tables.CompanyProfile;

import org.springframework.data.repository.CrudRepository;

public interface CompanyProfileRepository extends
                CrudRepository<CompanyProfile, Long> {
}
