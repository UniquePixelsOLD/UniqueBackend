package net.uniquepixels.uniquebackend.translations

import net.uniquepixels.uniquebackend.translations.dto.ProjectDto
import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationRepository : MongoRepository<ProjectDto, String> {

    fun existsByProjectName(name: String): Boolean

}