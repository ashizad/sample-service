package io.swagger.samples.inflector.springboot.tests;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import io.swagger.samples.inflector.springboot.SpringProfileCucumber;

@RunWith(SpringProfileCucumber.class)
@CucumberOptions(plugin = { "pretty" }, features = { "src/test/resources/features" }, strict = false, glue = {
		"io.swagger.samples.inflector.springboot" }, snippets = SnippetType.CAMELCASE, tags= {"@wip"})
@ActiveProfiles({ "integrationTest", "restApi", "h2", "localWs" })
public class InflectorApplicationRestApiH2LocalWsTests {

}
