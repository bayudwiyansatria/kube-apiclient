package com.bayudwiyansatria.spring.repository.jpa;

import com.bayudwiyansatria.spring.model.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * HomeJpaRepository
 * <p>
 * This interface represents the repository for the Home entity. It extends {@code JpaRepository} to
 * provide CRUD operations for the Home entity.
 *
 * <p>It is annotated with {@code @Repository} to indicate that it is a Spring Data repository.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Repository
public interface HomeJpaRepository extends JpaRepository<HomeEntity, Long> {

}
