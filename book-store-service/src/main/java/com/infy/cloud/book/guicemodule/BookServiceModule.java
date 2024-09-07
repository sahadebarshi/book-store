package com.infy.cloud.book.guicemodule;

import com.google.inject.AbstractModule;
import com.infy.cloud.book.BookStoreServiceConfiguration;
import com.infy.cloud.book.service.BookOrderService;
import com.infy.cloud.book.service.BookService;
import com.infy.cloud.book.serviceImpl.BookOrderServiceImpl;
import com.infy.cloud.book.serviceImpl.BookServiceImpl;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class BookServiceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(BookService.class).to(BookServiceImpl.class);
        bind(BookOrderService.class).to(BookOrderServiceImpl.class);
    }
}
