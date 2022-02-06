package com.example.weather.data.interfaces

/**
 * Interface for transform between entities
 * @param EntityFrom the entity being transformed
 * @param EntityTo The necessary entity
*/
interface IEntityMapper<EntityFrom, EntityTo> {
    fun mapEntity(entity : EntityFrom) : EntityTo
}