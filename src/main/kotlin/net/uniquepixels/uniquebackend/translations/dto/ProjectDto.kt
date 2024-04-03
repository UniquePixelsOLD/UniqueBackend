import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "projects")
data class ProjectDto(
    @Id var projectId: String,
    val projectName: String,
    val projectDescription: String,
    val languages: ArrayList<String>,
    // key -> lang -> translation
    var translations: HashMap<String, HashMap<String, String>>
) {
    // Method to preprocess map keys before storing
    fun preprocessMapKeys() {
        val newTranslations = HashMap<String, HashMap<String, String>>()
        translations.forEach { (lang, translationMap) ->
            val newLang = lang.replace(".", "_") // Replace dots with underscores
            val newTranslationMap = HashMap<String, String>()
            translationMap.forEach { (key, value) ->
                newTranslationMap[key.replace(".", "_")] = value // Replace dots with underscores
            }
            newTranslations[newLang] = newTranslationMap
        }
        translations = newTranslations
    }

    // Method to post-process map keys after retrieval
    fun postProcessMapKeys() {
        val newTranslations = HashMap<String, HashMap<String, String>>()
        translations.forEach { (lang, translationMap) ->
            val newLang = lang.replace("_", ".") // Replace underscores back to dots
            val newTranslationMap = HashMap<String, String>()
            translationMap.forEach { (key, value) ->
                newTranslationMap[key.replace("_", ".")] = value // Replace underscores back to dots
            }
            newTranslations[newLang] = newTranslationMap
        }
        translations = newTranslations
    }
}
