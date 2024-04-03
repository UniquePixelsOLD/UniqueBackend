package net.uniquepixels.uniquebackend.translations

import ProjectDto
import net.uniquepixels.uniquebackend.translations.dto.CreateProject
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/translation/")
@CrossOrigin(
    origins = ["*"],
    methods = [RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT]
)
class TranslationController {

    @Autowired
    lateinit var repo: TranslationRepository

    @GetMapping("/all")
    fun getProjectIds(): ResponseEntity<Any> {
        val findAll = this.repo.findAll()

        if (findAll.isEmpty())
            return ResponseEntity(HttpStatus.NOT_FOUND)

        findAll.forEach {
            it.postProcessMapKeys()
        }

        return ResponseEntity(
            findAll,
            HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    fun getProject(@PathVariable id: String): ResponseEntity<Any> {
        val optional = this.repo.findById(id)

        if (optional.isEmpty) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val get = optional.get()
        get.postProcessMapKeys()

        return ResponseEntity(optional.get(), HttpStatus.OK)
    }

    @PostMapping("create")
    fun createProject(@RequestBody dto: CreateProject): ResponseEntity<Any> {

        val languages = ArrayList<String>()
        val translations = HashMap<String, java.util.HashMap<String, String>>()

        languages.add("en")
        languages.add("de")
        languages.add("fr")
        languages.add("pl")

        val hashMap = HashMap<String, String>()
        hashMap["de"] = "Hallo"
        translations["project.key"] = hashMap

        val projectDto = ProjectDto(
            ObjectId().toString(), dto.projectName, dto.projectDescription,
            languages, translations
        )

        if (this.repo.existsByProjectName(dto.projectName)) {
            return ResponseEntity("Project name already existing!", HttpStatus.CONFLICT)
        }

        projectDto.preprocessMapKeys()
        return ResponseEntity(this.repo.insert(projectDto), HttpStatus.OK)
    }

    @PutMapping("{id}/{key}/{language}/add/{translation}")
    fun addTranslation(
        @PathVariable id: String, @PathVariable language: String, @PathVariable translation: String,
        @PathVariable key: String
    ): ResponseEntity<Any> {

        val findById = this.repo.findById(id)

        if (findById.isEmpty) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val dto = findById.get()
        dto.postProcessMapKeys()

        var hashMap = dto.translations[key]

        if (hashMap == null) {
            hashMap = HashMap()
        }

        hashMap[language] = translation

        dto.translations[key] = hashMap

        val response = dto.copy()
        dto.preprocessMapKeys()

        println(response)

        this.repo.save(dto)
        return ResponseEntity(response, HttpStatus.OK)
    }


}