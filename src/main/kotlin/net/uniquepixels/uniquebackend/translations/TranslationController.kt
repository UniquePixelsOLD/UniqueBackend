package net.uniquepixels.uniquebackend.translations

import net.uniquepixels.uniquebackend.translations.dto.CreateProject
import net.uniquepixels.uniquebackend.translations.dto.ProjectDto
import net.uniquepixels.uniquebackend.translations.dto.ShortProject
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/translation/")
@CrossOrigin(
    origins = arrayOf("*"),
    methods = arrayOf(RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT)
)
class TranslationController {

    @Autowired
    lateinit var repo: TranslationRepository

    @GetMapping("/all")
    fun getProjectIds(): ResponseEntity<Any> {
        return ResponseEntity(
            this.repo.findAll().stream().map { ShortProject(it.projectId, it.projectName, it.projectDescription) }.toList(),
            HttpStatus.OK
        )
    }

    @GetMapping("{id}")
    fun getProject(@PathVariable id: String): ResponseEntity<Any> {
        val optional = this.repo.findById(id)

        if (!optional.isEmpty) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        return ResponseEntity(optional.get(), HttpStatus.OK)
    }

    @PostMapping("create")
    fun createProject(@RequestBody dto: CreateProject): ResponseEntity<Any> {

        val projectDto = ProjectDto(ObjectId().toString(), dto.projectName, dto.projectDescription, HashMap())

        if (this.repo.existsByProjectName(dto.projectName)) {
            return ResponseEntity("Project name already existing!", HttpStatus.CONFLICT)
        }

        return ResponseEntity(this.repo.insert(projectDto), HttpStatus.OK)
    }


}