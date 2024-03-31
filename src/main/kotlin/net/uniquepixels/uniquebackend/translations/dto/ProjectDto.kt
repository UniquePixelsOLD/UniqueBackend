package net.uniquepixels.uniquebackend.translations.dto

import java.util.Locale

data class ProjectDto(var projectId: String, val projectName: String, val translations: HashMap<Locale, HashMap<String, String>>)