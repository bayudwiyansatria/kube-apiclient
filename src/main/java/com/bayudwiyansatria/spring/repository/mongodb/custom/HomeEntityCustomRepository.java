package com.bayudwiyansatria.spring.repository.mongodb.custom;

import com.bayudwiyansatria.spring.model.entity.HomeEntity;

/**
 * HomeEntityCustomRepository
 * <p>
 * Custom repository interface for HomeEntity. This interface provides custom methods for querying
 * HomeEntity objects.
 *
 * <p>The {@code findOne} method is declared to find a HomeEntity by its id and message.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public interface HomeEntityCustomRepository {

    /**
     * Finds a HomeEntity by its id and message.
     *
     * @param id      the id of the HomeEntity
     * @param message the message of the HomeEntity
     * @return the found HomeEntity, or null if no entity is found
     * @since 0.0.1
     */
    HomeEntity findOne(Long id, String message);
}
