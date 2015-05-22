package uk.sponte.google.sections.searchresults;

import org.openqa.selenium.support.FindBy;
import uk.sponte.automation.seleniumpom.PageElement;
import uk.sponte.automation.seleniumpom.PageSection;

/**
 * Created by n450777 on 01/05/15.
 */
public class SearchResult extends PageSection {
    @FindBy(css = ".r a")
    private PageElement link;

    @FindBy(css = ".st")
    public PageElement description;

    public void select() {
        this.link.click();
    }

    public String getUrl() {
        return this.link.getAttribute("href");
    }
}
