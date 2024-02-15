package net.uniquepixels.uniquebackend.translations

import net.uniquepixels.uniquebackend.translations.database.TranslationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("translations")
class TranslationController {

    @Autowired
    lateinit var repo: TranslationRepository

    @GetMapping("get")
    fun getAllTranslationsForProject(@RequestParam projectId: String): ResponseEntity<ProjectTranslation> {
        return ResponseEntity(this.repo.getByProjectId(projectId), HttpStatus.OK)
    }

    @GetMapping("test")
    fun test(): ResponseEntity<HashMap<String, String>> {
        val hashMap = HashMap<String, String>()
        hashMap["test"] = "hallo"
        hashMap["test2"] = "hallo2"

        return ResponseEntity(hashMap, HttpStatus.OK)
    }

    @GetMapping("get/translation")
    fun getTranslationForProject(@RequestParam projectId: String, @RequestParam translationKey: String): ResponseEntity<String> {
        return ResponseEntity(this.repo.getByProjectId(projectId).fileContent[translationKey], HttpStatus.OK)
    }

    @PostMapping("update")
    fun updateProjectTranslations(@RequestBody body: ProjectTranslation): ResponseEntity<ProjectTranslation> {
        return ResponseEntity(this.repo.save(body), HttpStatus.ACCEPTED)
    }


}