package com.example.mind.hw6.database;

public class TaskDbSchema {
    public static final String NAME = "tasks.db";
    public static final int VERSION = 1;

    public static final class TaskTable {
        public static final String NAME = "task";

        public static final class Colms {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String USER_UUID = "user_uuid";
            public static final String BOOLEAN_DONE = "doneBooleanInt";

        }
    }
    public static final class ProfileTable{
        public static final String NAME = "profile";

        public static final class Colms{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String E_MAIL = "email";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String CALL_NUMBER = "number";
            public static final String PASSWORD  ="password";
        }
    }
}
