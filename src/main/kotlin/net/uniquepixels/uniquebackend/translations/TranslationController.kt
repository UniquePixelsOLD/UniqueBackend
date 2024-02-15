package net.uniquepixels.uniquebackend.translations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TranslationController {

    @Autowired
    lateinit var repo: TranslationRepository

    @GetMapping("/translations/all")
    fun getAllTranslationsForProject(@RequestParam projectId: String): ResponseEntity<ProjectTranslation> {
        return ResponseEntity(this.repo.getByProjectId(projectId), HttpStatus.OK)
    }

    @GetMapping("/translations/get")
    fun getTranslationForProject(
        @RequestParam projectId: String,
        @RequestParam translationKey: String
    ): ResponseEntity<String> {
        return ResponseEntity(this.repo.getByProjectId(projectId).fileContent[translationKey], HttpStatus.OK)
    }

    @PostMapping("/translations/update")
    fun updateProjectTranslations(@RequestBody body: ProjectTranslation): ResponseEntity<ProjectTranslation> {
        return ResponseEntity(this.repo.save(body), HttpStatus.ACCEPTED)
    }


}