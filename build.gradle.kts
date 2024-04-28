import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.11"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}
taboolib {
    // 环境配置（例如模块、仓库地址等）
    // 此处列出所有可用选项，但通常均可省略
    env {
        // 调试模式
        debug = false
        // 是否在开发模式下强制下载依赖
        forceDownloadInDev = true
        // 中央仓库地址
        repoCentral = "https://maven.aliyun.com/repository/central"
        // TabooLib 仓库地址
        repoTabooLib = "https://repo.tabooproject.org/repository/releases"
        // 依赖下载目录
        fileLibs = "libraries"
        // 资源下载目录
        fileAssets = "assets"
        // 是否启用隔离加载器（即完全隔离模式）
        enableIsolatedClassloader = false
        // 安装模块
        install(UNIVERSAL)
        install(BUKKIT_ALL)
        install(KETHER)
        install(DATABASE)
    }
    // 版本配置
    // 此处列出所有可用选项，除 "TabooLib 版本" 外均省略
    version {
        // TabooLib 版本
        taboolib = "6.1.1-beta17"
        // Kotlinx Coroutines 版本（设为 null 表示禁用）
        coroutines = "1.7.3"
        // 跳过 Kotlin 加载
        skipKotlin = false
        // 跳过 Kotlin 重定向
        skipKotlinRelocate = false
        // 跳过 TabooLib 重定向
        skipTabooLibRelocate = false
    }
}

repositories {
    mavenCentral()
}
kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}
dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
    compileOnly("mysql:mysql-connector-java:8.0.23")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
