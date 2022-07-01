package pl.training.forecast.adapters.persistence

import pl.training.forecast.adapters.persistence.RoomForecastRepositoryMapper.Companion.CITY_ID
import pl.training.forecast.ports.model.DayForecast
import pl.training.forecast.ports.output.ForecastRepository

class RoomForecastRepositoryAdapter(private val forecastDao: ForecastDao, private val mapper: RoomForecastRepositoryMapper): ForecastRepository {

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        forecastDao.save(CityEntity(CITY_ID, city))
        forecastDao.save(forecast.map(mapper::toEntity))
    }

    override suspend fun load(city: String) = forecastDao.findByCity(city)?.forecast.map(mapper::toPorts)

    override suspend fun clear() {
        forecastDao.deleteAll()
    }

}