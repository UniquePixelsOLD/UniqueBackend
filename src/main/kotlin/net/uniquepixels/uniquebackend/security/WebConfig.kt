package net.uniquepixels.uniquebackend.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthInterceptor()).addPathPatterns("/**")
    }

//    override fun addCorsMappings(registry: CorsRegistry) {
//
//        LoggerFactory.getLogger(this::class.java).info("Core Mapping loading...")
//
//        registry.addMapping("/**")
//            .allowedMethods("*")
//            .allowedHeaders("*")
//            .allowCredentials(true)
//    }
}