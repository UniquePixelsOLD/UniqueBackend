package net.uniquepixels.uniquebackend.translations.database

import net.uniquepixels.uniquebackend.translations.ProjectTranslation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository("backend")
interface TranslationRepository: MongoRepository<ProjectTranslation, String> {

    fun getByProjectId(projectId: String): ProjectTranslation

}