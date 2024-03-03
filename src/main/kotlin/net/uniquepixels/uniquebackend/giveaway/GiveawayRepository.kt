package net.uniquepixels.uniquebackend.giveaway

import org.springframework.data.mongodb.repository.MongoRepository

interface GiveawayRepository: MongoRepository<Giveaway, Long> {

    fun getAllByActive(active: Boolean): List<Giveaway>

    fun getByGiveawayId(id: Long): Giveaway?

}