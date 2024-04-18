package com.myfirstdemotogithub.db;

import android.provider.BaseColumns;

public final class YourContract
{
    private YourContract()
    {
    }

    public static class YourEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "your_table_name";
        public static final String COLUMN_NAME_TITLE = "title";
    }
}
