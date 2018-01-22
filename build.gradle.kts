plugins {
    application
}

repositories {
    jcenter()
    maven("https://repo.spring.io/libs-snapshot")
}

val springVersion by extra { "5.0.3.BUILD-SNAPSHOT" }
val okHttpVersion by extra { "3.9.1" }
val reactorNettyVersion by extra { "0.7.2.RELEASE" }
val jacksonVersion by extra { "2.9.3" }

dependencies {
    compile("org.springframework:spring-webflux:$springVersion")
    compile("com.squareup.okhttp3:mockwebserver:$okHttpVersion")

    runtime("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    runtime("io.projectreactor.ipc:reactor-netty:$reactorNettyVersion")

    // Suppress Netty and OkHttp logging
    runtime("org.slf4j:slf4j-api:1.7.25")
    runtime("org.slf4j:slf4j-nop:1.7.25")
}
