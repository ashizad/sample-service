package io.swagger.samples.inflector.springboot;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.Link;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.samples.inflector.springboot.client.SampleServiceClient;
import io.swagger.samples.inflector.springboot.models.Resource;
import io.swagger.samples.inflector.springboot.models.UserDetail;
import static org.assertj.core.api.Java6Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { InflectorApplication.class, TestConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class StepDefs {

	@Autowired
	private SampleServiceClient client;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StubAccounts stubAccounts;

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

	@Given("^The User table has the following entry$")
	public void theUserTableHasTheFollowingEntry(Map<String, String> e) throws Throwable {
		String query = "insert into UserDetails values( '" + e.get("Name") + "','" + e.get("DoB") + "')";
		jdbcTemplate.update(query);
	}

	@When("^I request the User Details API$")
	public void iRequestTheUserDetailsAPI() throws Throwable {
		resource = client.followLink(client.getRoot().getLinks().stream()
				.filter(link -> link.getRels().contains("https://tomhoward.github.com/rels/user")).findAny().get());
	}

	@Given("^The user has the following accounts$")
	public void theUserHasTheFollowingAccounts(List<Map<String, String>> accounts) throws Throwable {
		stubAccounts.setAccounts(accounts);
	}

	@When("^I request the Account API$")
	public void iRequestTheAccountAPI() throws Throwable {
		resource = client.followLink(client.getRoot().getLinks().stream()
				.filter(link -> link.getRels().contains("https://tomhoward.github.com/rels/accounts")).findAny().get());
	}

	@Then("^I'll get the following account details$")
	public void iLlGetTheFollowingAccountDetails(DataTable arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		throw new PendingException();
	}

	@SuppressWarnings("deprecation")
	@Then("^I'll get the following user details$")
	public void iLlGetTheFollowingUserDetails(List<UserDetail> userDetails) throws Throwable {
		UserDetail expectedUSer = new UserDetail("John","Doe","1768-01-01");
		UserDetail  user = userDetails.stream().findAny().get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
		LocalDateTime date = LocalDateTime.parse(user.getDob(), formatter);
		user.setDob(date.toString());
		assertThat(user.equals(expectedUSer)).isTrue();
		
		//Assert.assertEquals(expectedUSer, user);
	}

	@When("^set the preferred name to \"([^\"]*)\"$")
	public void setThePreferredNameTo(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
