package net.uniquepixels.uniquebackend.translations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Locale

@RestController
class TranslationController {

    @Autowired
    lateinit var repo: TranslationRepository

    @GetMapping("/translations/all")
    fun getAllTranslationsForProject(@RequestParam projectId: String, @RequestParam locale: Locale): ResponseEntity<ProjectTranslation> {
        return ResponseEntity(this.repo.getByProjectIdAndLocale(projectId, locale), HttpStatus.OK)
    }

    @GetMapping("/translations/get")
    fun getTranslationForProject(
        @RequestParam projectId: String,
        @RequestParam translationKey: String,
        @RequestParam locale: Locale
    ): ResponseEntity<String> {
        return ResponseEntity(this.repo.getByProjectIdAndLocale(projectId, locale).fileContent[translationKey], HttpStatus.OK)
    }

    @PostMapping("/translations/update")
    fun updateProjectTranslations(@RequestBody body: ProjectTranslation): ResponseEntity<ProjectTranslation> {
        return ResponseEntity(this.repo.save(body), HttpStatus.ACCEPTED)
    }


}