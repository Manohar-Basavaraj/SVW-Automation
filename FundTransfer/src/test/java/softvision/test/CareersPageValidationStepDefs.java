package softvision.test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import validators.DropdownValidator;
import validators.UrlValidator;
import validators.WebpageSpellChecker;

public class CareersPageValidationStepDefs {

	protected WebDriver driver;
	private String autUrl = "https://www.softvision.com/careers/";
	private Map<String, Integer> responses;
	private Map<String, String> misspelledSuggestions;
	private boolean isSortedInNatural, isMultiple;
	private String defaultSelection;

	@Before
	public void setUp() {
		String driverName = "webdriver.chrome.driver";
		String driverPath = "C:\\chromedriver_win32\\chromedriver.exe";
		System.setProperty(driverName, driverPath);
		driver = new ChromeDriver();
	}

	@Given("the user is on Softvision careers page")
	public void theUserIsOnCareersPage() {
		driver.get(autUrl);
		driver.manage().window().maximize();
	}

	@When("the user checks for the broken links in the page")
	public void theUserValidatesBrokenLinks() {
		List<WebElement> links = null;

		try {
			links = driver.findElements(By.tagName("a"));
		} catch (Exception e) {
			System.out.println("Unable to get the links in the AUT, error message below:");
			e.getMessage();
		}

		System.out.println("Total no. of links in the page: " + links.size());
		responses = UrlValidator.validateLinks(links);
	}

	@Then("the user should be shown all the broken links")
	public void theUserShownResults() {
		UrlValidator.displayLinkResponses(responses);
	}

	@When("the user checks for spelling errors in the page")
	public void theUserValidatesSpellingErrors() {
		String webpageText = null;
		WebpageSpellChecker jazzySpellChecker = new WebpageSpellChecker();

		try {
			webpageText = driver.findElement(By.tagName("body")).getText();
		} catch (Exception e) {
			System.out.println("Unable to get the links in the AUT, error message below:");
			e.getMessage();
		}

		misspelledSuggestions = jazzySpellChecker.showSuggestions(webpageText);
	}

	@Then("the user should be shown all the misspelled words and suggestions")
	public void theUserShownWordsSuggestions() {
		for (Entry<String, String> suggestion : misspelledSuggestions.entrySet()) {
			System.out.println("Misspelled word: " + suggestion.getKey());
			System.out.println("Correction suggestions: " + suggestion.getValue());
		}
	}

	@When("user validates that the options are sorted in \"([^\"]*)\" dropdown")
	public void theUserValidatesDropdownSort(String dropdownName) {
		String xpath = null;

		if (dropdownName.equalsIgnoreCase("locations"))
			xpath = "//*[@id='first-section']//select[@class='locationselect']";
		if (dropdownName.equalsIgnoreCase("guilds"))
			xpath = "//*[@id='first-section']//select[@class='guildselect']";

		WebElement selectDropdown = DropdownValidator.findSelectDropdown(driver, xpath);

		isSortedInNatural = DropdownValidator.isSortedInNaturalOrder(selectDropdown);
	}

	@Then("the sort validation result should be displayed")
	public void theUserShownSortValidationResult() {
		System.out.println("Are the dropdown options sorted in natural order? : " + isSortedInNatural);
	}

	@When("user checks whether the \"([^\"]*)\" dropdown is single or multi-select")
	public void theUserChecksDropdownType(String dropdownName) {
		String xpath = null;

		if (dropdownName.equalsIgnoreCase("locations"))
			xpath = "//*[@id='first-section']//select[@class='locationselect']";
		if (dropdownName.equalsIgnoreCase("guilds"))
			xpath = "//*[@id='first-section']//select[@class='guildselect']";

		WebElement selectDropdown = DropdownValidator.findSelectDropdown(driver, xpath);
		
		isMultiple = DropdownValidator.isMultipleSelect(selectDropdown);
	}

	@Then("the select type validation result should be displayed")
	public void theUserShownDropdownType() {
		System.out.println("Is the dropdown of the type SINGLE or MULTIPLE select? : " + (isMultiple?"MULTI-SELECT":"SINGLE SELECT"));
	}

	@When("user checks for the default selection in \"([^\"]*)\" dropdown")
	public void theUserChecksDefaultSelection(String dropdownName) {
		String xpath = null;

		if (dropdownName.equalsIgnoreCase("locations"))
			xpath = "//*[@id='first-section']//select[@class='locationselect']";
		if (dropdownName.equalsIgnoreCase("guilds"))
			xpath = "//*[@id='first-section']//select[@class='guildselect']";

		WebElement selectDropdown = DropdownValidator.findSelectDropdown(driver, xpath);
		
		defaultSelection = DropdownValidator.getDefaultSelection(selectDropdown);
	}

	@Then("the default option should be displayed")
	public void theUserShownDefaultOption() {
		System.out.println("The default selection for this dropdown is: " + defaultSelection);
	}

	@After
	public void shutDown() {
		driver.close();
		driver.quit();
	}
}
