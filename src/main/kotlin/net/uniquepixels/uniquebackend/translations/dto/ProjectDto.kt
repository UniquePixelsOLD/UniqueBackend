package net.uniquepixels.uniquebackend.translations.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "projects")
data class ProjectDto(
    @Id var projectId: String,
    val projectName: String,
    val projectDescription: String,
    val translations: HashMap<String, HashMap<String, String>>
)