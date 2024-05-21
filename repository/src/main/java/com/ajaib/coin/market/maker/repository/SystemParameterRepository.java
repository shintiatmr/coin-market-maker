package com.ajaib.coin.market.maker.repository;

import com.ajaib.coin.market.maker.entity.SystemParameter;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, UUID> {

  Optional<SystemParameter> findFirstByVariableAndDeletedAtIsNull(String variable);

}
