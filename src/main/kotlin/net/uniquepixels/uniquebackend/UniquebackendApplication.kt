package net.uniquepixels.uniquebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.web.SecurityFilterChain

@SpringBootApplication()
class UniquebackendApplication

fun main(args: Array<String>) {
    runApplication<UniquebackendApplication>(*args)
}

//@Bean
//fun filterChain(http: HttpSecurity): SecurityFilterChain {
//
//
//
//    return http
//        .httpBasic {
//            it.disable()
//        }
//        .csrf {
//            it.disable()
//        }
//        .authorizeHttpRequests {
//            it.anyRequest().permitAll()
//        }.build()
//}

//@Bean
//fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
//    return http.cors {
//        it.disable()
//    }.csrf {
//        it.disable()
//    }.httpBasic {
//        it.disable()
//    }.formLogin {
//        it.disable()
//    }.build()
//}
