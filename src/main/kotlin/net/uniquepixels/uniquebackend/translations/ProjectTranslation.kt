package net.uniquepixels.uniquebackend.translations

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Locale

@Document("translations")
data class ProjectTranslation(
    @Id val projectId: String,
    val translator: List<String>,
    var fileContent: HashMap<String, String>,
    val locale: Locale
)
