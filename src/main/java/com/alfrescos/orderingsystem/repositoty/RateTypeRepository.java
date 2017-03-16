package com.alfrescos.orderingsystem.repositoty;

import com.alfrescos.orderingsystem.entity.RateType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 16-Mar-17.
 */
@Repository
public interface RateTypeRepository extends CrudRepository<RateType, Long>{
}
