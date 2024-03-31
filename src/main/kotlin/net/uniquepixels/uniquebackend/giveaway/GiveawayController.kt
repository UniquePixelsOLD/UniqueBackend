package net.uniquepixels.uniquebackend.giveaway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@RestController
class GiveawayController {

    @Autowired
    lateinit var repository: GiveawayRepository

    @Autowired
    lateinit var wsClient: SimpMessagingTemplate

    init {
        this.endGiveaways()
    }

    private fun endGiveaways() {

        Executors.newSingleThreadScheduledExecutor().schedule({

            this.repository.getAllByActive(true).forEach {

                val time = it.started + it.duration

                if (time >= Instant.now().epochSecond)
                    setFinished(it)

            }

        }, 30L, TimeUnit.SECONDS)

    }

    private fun setFinished(giveaway: Giveaway) {
        giveaway.active = false

        this.repository.deleteById(giveaway.giveawayId)
        this.repository.save(giveaway)

        val randomMember = giveaway.enteredMembers[Random().nextInt(0, giveaway.enteredMembers.size)]


        this.wsClient.convertAndSend("/topic/giveaway/ending", GiveawayFinished(giveaway.messageId, randomMember))
    }

    @MessageMapping("/giveaway/ending")
    @SendTo("/response/")
    fun endingGiveaway(giveawayFinished: GiveawayFinished): GiveawayFinished {
        return giveawayFinished
    }

    @GetMapping("/giveaway")
    fun getAllActiveGiveaways(): ResponseEntity<List<Giveaway>> {
        val allByActive = repository.getAllByActive(true)
        return ResponseEntity(allByActive, HttpStatus.OK)
    }

    @GetMapping("/giveaway/{id}")
    fun getGiveawayById(@PathVariable id: Long): ResponseEntity<Giveaway> {
        val giveaway = repository.getByGiveawayId(id)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(giveaway, HttpStatus.OK)
    }

    @PutMapping("/giveaway/{id}/add/{member}")
    fun addMember(@PathVariable id: Long, @PathVariable member: Long): ResponseEntity<Boolean> {

        val giveaway = repository.getByGiveawayId(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        if (giveaway.enteredMembers.contains(member))
            return ResponseEntity(false, HttpStatus.OK)

        giveaway.enteredMembers.add(member)
        repository.delete(giveaway)
        repository.save(giveaway)

        return ResponseEntity(true, HttpStatus.OK)
    }

    @PutMapping("/giveaway/{id}/remove/{member}")
    fun removeMember(@PathVariable id: Long, @PathVariable member: Long): ResponseEntity<Boolean> {

        val giveaway = repository.getByGiveawayId(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        if (!giveaway.enteredMembers.contains(member))
            return ResponseEntity(false, HttpStatus.OK)

        giveaway.enteredMembers.remove(member)
        repository.delete(giveaway)
        repository.save(giveaway)

        return ResponseEntity(true, HttpStatus.OK)
    }

    @PostMapping("/giveaway/create")
    fun createGiveaway(@RequestBody giveaway: Giveaway): ResponseEntity<Giveaway> {
        giveaway.giveawayId = repository.count() + 1
        repository.save(giveaway)
        return ResponseEntity(giveaway, HttpStatus.CREATED)
    }

    @DeleteMapping("/giveaway/delete/{id}")
    fun deleteGiveaway(@PathVariable id: Long): ResponseEntity<Boolean> {
        repository.deleteById(id)
        return ResponseEntity(!repository.existsById(id), HttpStatus.ACCEPTED)
    }

    @PutMapping("/giveaway/publish")
    fun publishGiveaway(@RequestBody id: Long): ResponseEntity<Giveaway> {
        val giveaway = repository.getByGiveawayId(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)

        if (giveaway.active) {
            return ResponseEntity(giveaway, HttpStatus.CONFLICT)
        }

        giveaway.active = true
        giveaway.started = Instant.now().epochSecond

        this.repository.deleteById(id)
        this.repository.save(giveaway)

        return ResponseEntity(giveaway, HttpStatus.ACCEPTED)
    }

}