package com.infy.cloud.book;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.infy.cloud.book.guicemodule.ConfigurationModule;
import com.infy.cloud.book.guicemodule.BookServiceModule;
import com.infy.cloud.book.guicemodule.ControllerModule;
import com.infy.cloud.book.guicemodule.ResourceModule;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.servlets.CacheBustingFilter;
import jakarta.servlet.DispatcherType;
import ru.vyarus.dropwizard.guice.GuiceBundle;

import java.util.EnumSet;

public class BookStoreServiceApplication extends Application<BookStoreServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BookStoreServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "Book Store Service";
    }

    @Override
    public void initialize(final Bootstrap<BookStoreServiceConfiguration> bootstrap) {
        var guiceBundle = GuiceBundle.builder().modules(new ConfigurationModule(),
                                                new BookServiceModule(),
                                                new ControllerModule(),
                                                new ResourceModule())
                                               .build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(final BookStoreServiceConfiguration configuration,
                    final Environment env) {
      env.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      // prevent caching index.html, so that the documentation is always upto date
        env.servlets().addFilter("index-cache-busting-filter", new CacheBustingFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                        false, "/");
        // prevent caching openapi files, so that the documentation is always upto date
        env.servlets().addFilter("openapi-cache-busting-filter", new CacheBustingFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                        false, "/openapi.json", "/openapi.yaml");

    }

}
