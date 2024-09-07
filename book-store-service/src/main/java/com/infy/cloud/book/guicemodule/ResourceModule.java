package com.infy.cloud.book.guicemodule;

import com.google.inject.AbstractModule;
import com.infy.cloud.book.db.DBConnection;

public class ResourceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
         bind(DBConnection.class);
    }
}
