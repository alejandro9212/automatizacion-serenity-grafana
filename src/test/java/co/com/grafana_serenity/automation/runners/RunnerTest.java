package co.com.grafana_serenity.automation.runners;


import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@IncludeTags("loginThePage")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "co.com.grafana_serenity.automation.stepdefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.cucumber.core.plugin.SerenityReporterParallel,pretty")
@ConfigurationParameter(key = "serenity.extension.packages", value = "co.com.grafana_serenity.automation.utils.Listener")


public class RunnerTest {
}
