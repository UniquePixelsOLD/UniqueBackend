package net.uniquepixels.uniquebackend.giveaway

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("giveaway")
data class Giveaway(
    @Id var giveawayId: Long,
    val starterId: Long,
    val price: String,
    val maxWinners: Int,
    var started: Long,
    val duration: Long,
    var active: Boolean,
    var messageId: Long,
    val enteredMembers: ArrayList<Long>
)