package com.backend.timkiemdong.repository;

import com.backend.timkiemdong.entity.ParaCurrencyRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParaCurrencyRateRepository extends ProcedureQueryRepo,NativeQueryRepository,JpaSpecificationExecutor<ParaCurrencyRate>,JpaRepository<ParaCurrencyRate, Long> {

}
