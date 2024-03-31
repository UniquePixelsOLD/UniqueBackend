package net.uniquepixels.uniquebackend.giveaway

import com.google.gson.Gson
import org.springframework.web.socket.WebSocketMessage

data class GiveawayFinished(val messageId: Long, val winner: Long): WebSocketMessage<String> {

    private fun bake(): String {
        return Gson().toJson(this)
    }
    override fun getPayload(): String {
        return this.bake()
    }

    override fun getPayloadLength(): Int {
        return this.bake().length
    }

    override fun isLast(): Boolean {
        return false
    }

}
