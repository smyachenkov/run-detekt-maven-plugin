package com.smyachenkov.rundetekt

import io.gitlab.arturbosch.detekt.cli.CliArgs
import io.gitlab.arturbosch.detekt.cli.runners.Runner
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo

@Mojo(name = "detekt", defaultPhase = LifecyclePhase.VERIFY)
class DetektMojo : AbstractMojo() {

    override fun execute() {
        Runner(CliArgs()).execute()
    }
}
