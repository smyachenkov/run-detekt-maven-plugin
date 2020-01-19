package com.smyachenkov.rundetekt

import com.smyachenkov.ConfigLocator
import io.gitlab.arturbosch.detekt.cli.CliArgs
import io.gitlab.arturbosch.detekt.cli.runners.Runner
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject

@Mojo(name = "detekt", defaultPhase = LifecyclePhase.VERIFY)
class DetektMojo : AbstractMojo() {

    @Parameter(property = "detekt.auto-correct")
    private val autoCorrect: String? = null

    @Parameter(property = "detekt.baseline")
    private val baseline: String? = null

    @Parameter(property = "detekt.build-upon-default-config")
    private val buildUponDefaultConfig: Boolean = false

    @Parameter(property = "detekt.help")
    private val help: Boolean = false

    @Parameter(property = "detekt.classpath")
    private val classpath: String? = null

    @Parameter(property = "detekt.config")
    private val config: String? = null

    @Parameter(property = "detekt.config-resource")
    private val configResource: String? = null

    @Parameter(property = "detekt.create-baseline")
    private val createBaseline: Boolean = false

    @Parameter(property = "detekt.debug")
    private val debug: Boolean = false

    @Parameter(property = "detekt.disable-default-rulesets")
    private val disableDefaultRulesets: Boolean = false

    @Parameter(property = "detekt.excludes")
    private val excludes: String? = null

    @Parameter(property = "detekt.fail-fast")
    private val failFast: Boolean = false

    @Parameter(property = "detekt.generate-config")
    private val generateConfig: Boolean = false

    @Parameter(property = "detekt.includes")
    private val includes: String? = null

    @Parameter(property = "detekt.input")
    private val input: String? = null

    @Parameter(property = "detekt.language-version")
    private val languageVersion: String? = null

    @Parameter(property = "detekt.jvm-target")
    private val jvmTarget: String? = null

    @Parameter(property = "detekt.parallel")
    private val parallel: Boolean = false

    @Parameter(property = "detekt.plugins")
    private val plugins: String? = null

    @Parameter(property = "detekt.report")
    private val report: String? = null

    @Parameter(defaultValue = "\${project}", readonly = true)
    var mavenProject: MavenProject? = null

    override fun execute() {
        val resolvedConfigPath = ConfigLocator(mavenProject, config).findPath()
        val cliArgs = CliArgs.parse(
                mutableListOf<String>().apply {
                    addParam("--auto-correct", autoCorrect)
                    addParam("--baseline", baseline)
                    addFlag("--build-upon-default-config", buildUponDefaultConfig)
                    addParam("--classpath", classpath)
                    addParam("--config", resolvedConfigPath)
                    addParam("--config-resource", configResource)
                    addFlag("--create-baseline", createBaseline)
                    addFlag("--debug", debug)
                    addFlag("--disable-default-rulesets", disableDefaultRulesets)
                    addParam("--excludes", excludes)
                    addFlag("--fail-fast", failFast)
                    addFlag("--generate-config", generateConfig)
                    addFlag("--help", help)
                    addParam("--includes", includes)
                    addParam("--input", input)
                    addParam("--language-version", languageVersion)
                    addParam("--jvm-target", jvmTarget)
                    addFlag("--parallel", parallel)
                    addParam("--plugins", plugins)
                    addParam("--report", report)
                }.toTypedArray()
        )
        Runner(cliArgs).execute()
    }

    private fun MutableList<String>.addParam(paramName: String, value: String?) {
        value?.let {
            this.add(paramName)
            this.add(value)
        }
    }

    private fun MutableList<String>.addFlag(paramName: String, isPresent: Boolean) {
        if (isPresent) {
            this.add(paramName)
        }
    }
}
