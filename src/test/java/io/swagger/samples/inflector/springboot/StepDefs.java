package io.swagger.samples.inflector.springboot;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Link;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.samples.inflector.springboot.client.SampleServiceClient;
import io.swagger.samples.inflector.springboot.models.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { InflectorApplication.class, TestConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class StepDefs {

	@Autowired
	private SampleServiceClient client;

	private Resource resource;

	@When("^I request the root API$")
	public void i_request_the_root_API() throws Throwable {
		resource = client.getRoot();
	}

	@Then("^I'll get links to the following endpoints$")
	public void illGetLinksToTheFollowingEndpoints(List<String> titles) throws Throwable {
		List<Link> links = resource.getLinks();
		List<String> linkTitles = links.stream().map(link -> link.getTitle()).collect(Collectors.toList());
		assertThat(linkTitles, contains(titles.toArray()));
	}

}
