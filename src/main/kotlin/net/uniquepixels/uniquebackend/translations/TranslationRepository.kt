package net.uniquepixels.uniquebackend.translations

import ProjectDto
import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationRepository : MongoRepository<ProjectDto, String> {

    fun existsByProjectName(name: String): Boolean

}