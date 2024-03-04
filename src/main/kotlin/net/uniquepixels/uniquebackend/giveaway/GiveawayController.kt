package net.uniquepixels.uniquebackend.giveaway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GiveawayController {

    @Autowired
    lateinit var repository: GiveawayRepository

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
        giveaway.started = System.currentTimeMillis()
        giveaway.giveawayId = repository.count() + 1
        repository.save(giveaway)
        return ResponseEntity(giveaway, HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/giveaway/delete/{id}")
    fun deleteGiveaway(@PathVariable id: Long): ResponseEntity<Boolean> {
        repository.deleteById(id)
        return ResponseEntity(!repository.existsById(id), HttpStatus.ACCEPTED)
    }

}