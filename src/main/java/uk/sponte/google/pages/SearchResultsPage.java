package uk.sponte.google.pages;

import org.openqa.selenium.support.FindBy;
import uk.sponte.automation.seleniumpom.PageElement;
import uk.sponte.google.sections.searchresults.SearchResults;

/**
 * Created by n450777 on 01/05/15.
 */
public class SearchResultsPage {
    public PageElement resultStats;

    @FindBy(id = "center_col")
    public SearchResults searchResults;
}
