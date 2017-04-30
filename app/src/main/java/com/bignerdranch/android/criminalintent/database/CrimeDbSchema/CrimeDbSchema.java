package com.bignerdranch.android.criminalintent.database.CrimeDbSchema;

/**
 * Created by Administrator on 2017/4/30/030.
 */

public class CrimeDbSchema {
    public static final class CrimeTable{
        public static final String NAME = "crimes";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String Content = "content";
            public static final String DATE = "date";
            public static final String SOLVE = "solve";
        }
    }
}
