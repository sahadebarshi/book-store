package com.infy.cloud.book.guicemodule;

import com.google.inject.AbstractModule;
import com.infy.cloud.book.BookStoreServiceConfiguration;
import com.infy.cloud.book.resources.BookOrderController;
import com.infy.cloud.book.resources.BookServiceController;


public class ControllerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(BookOrderController.class);
        bind(BookServiceController.class);
    }
}
