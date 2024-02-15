package net.uniquepixels.uniquebackend.translations

import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationRepository : MongoRepository<ProjectTranslation, String> {

    fun getByProjectId(projectId: String): ProjectTranslation

}