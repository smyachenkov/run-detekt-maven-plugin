package com.smyachenkov

import org.apache.maven.model.Resource
import org.apache.maven.project.MavenProject
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class ConfigLocatorTest {

    @Test
    fun `should return null if configPath is absent`() {
        val actual = ConfigLocator(MavenProject(), null).findPath()
        assertNull(actual)
    }

    @Test
    fun `should return null if mavenProject is absent`() {
        val actual = ConfigLocator(null, "detekt.yml").findPath()
        assertNull(actual)
    }

    @Test
    fun `should return configPath if it is an absolute path`() {
        val absolutePath = Paths.get("src/test/resources/detekt.yml").toAbsolutePath()
        val actual = ConfigLocator(MavenProject(), absolutePath.toString()).findPath()
        assertEquals(absolutePath.toString(), actual)
    }

    @Test
    fun `should return file from resources if present`() {
        val project = MavenProject().apply {
            this.addResource(
                    Resource().apply {
                        this.directory = "src/test/resources/config/"
                    }
            )
        }
        val resolvedPath = ConfigLocator(project, "detekt.yml").findPath()
        assertNotNull(resolvedPath)
        val expected = Paths.get("src/test/resources/config/detekt.yml").toAbsolutePath().toString()
        val actual = Paths.get(resolvedPath).toAbsolutePath().toString()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return file from parent resources if has parent resources`() {
        val parentProject = MavenProject().apply {
            this.addResource(
                    Resource().apply {
                        this.directory = "src/test/resources/config/"
                    }
            )
        }
        val childProject = MavenProject().apply {
            this.parent = parentProject
        }
        val resolvedPath = ConfigLocator(childProject, "detekt.yml").findPath()
        assertNotNull(resolvedPath)
        val expected = Paths.get("src/test/resources/config/detekt.yml").toAbsolutePath().toString()
        val actual = Paths.get(resolvedPath).toAbsolutePath().toString()
        assertEquals(expected, actual)
    }
}
