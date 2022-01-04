package com.example.weather.data.mappers

import com.example.weather.data.db.entities.CityEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.CityViewEntity

class CityViewEntityMapper : IEntityMapper<CityEntity, CityViewEntity> {
    override fun mapEntity(entity: CityEntity): CityViewEntity {
        return CityViewEntity(
            id = entity.id,
            country = entity.country,
            name = entity.name,
            coord = CityViewEntity.Coord(
                lat = entity.lat,
                lon = entity.lon
            ),
            isCurrent = entity.isCurrent
        )
    }
}