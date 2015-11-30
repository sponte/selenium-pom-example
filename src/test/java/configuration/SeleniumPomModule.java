package configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.matcher.Matchers;
import org.openqa.selenium.WebDriver;
import uk.sponte.automation.seleniumpom.PageFactory;
import uk.sponte.automation.seleniumpom.dependencies.DependencyInjector;
import uk.sponte.automation.seleniumpom.dependencies.InjectionError;

/**
 * Created by n450777 on 30/11/2015.
 */
public class SeleniumPomModule extends AbstractModule
        implements DependencyInjector {

    private Injector injector;

    public Injector getInjector() {
        if (this.injector == null)
            injector = Guice.createInjector(
                    Stage.PRODUCTION,
                    this
            );

        return injector;
    }

    @Override
    public <T> T get(Class<T> klass) throws InjectionError {
        return getInjector().getInstance(klass);
    }

    @Override
    protected void configure() {
        bind(WebDriver.class)
            .toProvider(WebDriverProvider.class)
            .in(Singleton.class);

        bind(PageFactory.class)
            .toInstance(new PageFactory(this));

        // PageObjectModelTypeListener interrogates each type to see if it's a page object.
        // It does this by looking at field types and annotations
        bindListener(Matchers.any(), new PageObjectModelTypeListener());
    }
}
