package com.example.weather.data.mappers

import com.example.weather.data.db.entities.CityEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.CityViewEntity

class CityEntityMapper : IEntityMapper<CityViewEntity, CityEntity> {
    override fun mapEntity(entity: CityViewEntity): CityEntity {
        return CityEntity(
            id = entity.id,
            lon = entity.coord.lon,
            lat = entity.coord.lat,
            name = entity.name,
            country = entity.country,
            isCurrent = entity.isCurrent
        )
    }
}