import configuration.SeleniumPomModule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.google.Homepage;
import pages.google.SearchResultsPage;
import pages.google.sections.searchresults.SearchResult;
import pages.wikipedia.Article;
import uk.sponte.automation.seleniumpom.PageFactory;
import uk.sponte.automation.seleniumpom.dependencies.DependencyInjector;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * Created by n450777 on 30/11/2015.
 */
public class GoogleGuiceDependencyInjectionTest {

    private PageFactory pageFactory;

    @Before
    public void setupDependencyInjection() {
        DependencyInjector di = new SeleniumPomModule();
        pageFactory = di.get(PageFactory.class);
        pageFactory.getDriver().navigate().to("https://www.google.com/ncr");
    }

    @After
    public void teardown() {
        pageFactory.getDriver().quit();
    }

    @Test
    public void runExampleTest() throws TimeoutException {
        Homepage homepage = pageFactory.get(Homepage.class);
        homepage.searchForm.searchFor("selenium");

        SearchResultsPage searchResultsPage = pageFactory.get(SearchResultsPage.class);
        searchResultsPage.resultStats.waitFor();

        System.out.printf("Found %d results%n", searchResultsPage.searchResults.results.size());

        Optional<SearchResult> searchResult = searchResultsPage
                .searchResults
                .results
                .stream()
                .filter(result -> result.getUrl().getHost().equals("en.wikipedia.org"))
                .findAny();

        if(!searchResult.isPresent())
            throw new RuntimeException("Could not find a link with wikipedia in url");

        String expectedSearchResultTitle = searchResult.get().title.getText();

        searchResult.get().select();

        Article article = pageFactory.get(Article.class);
        article.firstHeading.waitFor();

        Assert.assertEquals(expectedSearchResultTitle, pageFactory.getDriver().getTitle());
    }
}
