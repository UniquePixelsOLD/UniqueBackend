package net.uniquepixels.uniquebackend.translations

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Locale

interface TranslationRepository : MongoRepository<ProjectTranslation, String> {

    fun getByProjectIdAndLocale(projectId: String, locale: Locale): ProjectTranslation
    fun getByProjectId(projectId: String): List<ProjectTranslation>

}