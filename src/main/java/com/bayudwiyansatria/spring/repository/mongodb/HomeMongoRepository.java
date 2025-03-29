package com.bayudwiyansatria.spring.repository.mongodb;

import com.bayudwiyansatria.spring.model.entity.HomeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * HomeMongoRepository
 * <p>
 * This interface represents the repository for the Home entity. It extends {@code MongoRepository}
 * to provide CRUD operations for the Home entity.
 *
 * <p>It is annotated with {@code @Repository} to indicate that it is a Spring Data repository.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public interface HomeMongoRepository extends MongoRepository<HomeEntity, Long> {

}
