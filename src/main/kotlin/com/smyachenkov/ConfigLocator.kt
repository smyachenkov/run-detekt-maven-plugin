package com.smyachenkov

import org.apache.maven.model.Resource
import org.apache.maven.project.MavenProject
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


class ConfigLocator(private val mavenProject: MavenProject?,
                    private val configPath: String?) {
    fun findPath(): String? {
        if (mavenProject == null|| configPath == null) {
            return null
        }
        if (Paths.get(configPath).isAbsolute) {
            return configPath
        }
        mavenProject.resources?.findFile(configPath)?.let { return it }
        mavenProject.parent?.resources?.findFile(configPath).let { return it }
    }

    private fun List<Resource>.findFile(fileName: String): String? {
        return this.map {
            Paths.get(it.directory + File.separator + configPath)
        }.filter {
            Files.exists(it)
        }.map {
            it.toString()
        }.firstOrNull()
    }
}
