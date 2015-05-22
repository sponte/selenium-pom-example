package uk.sponte.google;

import org.openqa.selenium.WebDriver;
import uk.sponte.automation.seleniumpom.PageFactory;
import uk.sponte.google.pages.Homepage;
import uk.sponte.google.pages.SearchResultsPage;
import uk.sponte.google.sections.searchresults.SearchResult;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * Created by n450777 on 01/05/15.
 */
public class Application {
    public static void main(String[] args) {
        System.setProperty("selenium.webdriver", "firefox");

        PageFactory pageFactory = new PageFactory();

        WebDriver driver = pageFactory.getDriver();
        driver.navigate().to("http://www.google.com/ncr");

        try {
            new Application().run(pageFactory);
        } catch(RuntimeException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public void run(PageFactory pageFactory) throws TimeoutException {
        Homepage homepage = pageFactory.get(Homepage.class);
        homepage.searchForm.searchFor("selenium");

        SearchResultsPage searchResultsPage = pageFactory.get(SearchResultsPage.class);
        searchResultsPage.resultStats.waitFor();

        System.out.printf("Found %d results%n", searchResultsPage.searchResults.results.size());

        Optional<SearchResult> searchResult = searchResultsPage
                .searchResults
                .results
                .stream()
                .filter(result -> result.getUrl().contains("wikipedia"))
                .findAny();

        if(!searchResult.isPresent())
            throw new RuntimeException("Could not find a link with wikipedia in url");

        searchResult.get().select();

        System.out.printf("Selected page title: %s%n", pageFactory.getDriver().getTitle());
    }
}
