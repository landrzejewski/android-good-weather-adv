package pl.training.goodweather.forecast.adapter.persistence.room

import pl.training.goodweather.forecast.adapter.persistence.room.RoomForecastRepositoryMapper.Companion.CITY_ID
import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.model.DayForecast

class RoomForecastRepositoryAdapter(private val forecastDao: ForecastDao, private val mapper: RoomForecastRepositoryMapper) : ForecastRepository {

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        forecastDao.save(CityEntity(CITY_ID, city))
        forecastDao.save(forecast.map(mapper::toEntity))
    }

    override suspend fun load(city: String) = forecastDao.findAll(city)?.forecast?.map(mapper::toModel) ?: emptyList()

    override suspend fun clear() {
        forecastDao.deleteAll()
    }

}