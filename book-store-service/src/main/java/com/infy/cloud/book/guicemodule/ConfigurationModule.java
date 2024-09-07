package com.infy.cloud.book.guicemodule;

import com.infy.cloud.book.BookStoreServiceConfiguration;
import com.infy.cloud.book.configuration.BookPriceConfiguration;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class ConfigurationModule extends DropwizardAwareModule<BookStoreServiceConfiguration>
{
    @Override
    protected void configure()
    {
       bind(BookPriceConfiguration.class).toInstance(configuration().getPriceMapping());
    }
}
