package in.ceeq.alec.provider.contentprovider;

import android.net.Uri;


public final class ContactContract {

    public static final String AUTHORITY = "in.ceeq.alec.provider";

    public static final class Contacts extends BaseColumns {

        public static final String PATH = "contacts";

        public static final String SERVER_ID = "server_id";

        public static final String FIRST_NAME = "first_name";

        public static final String LAST_NAME = "last_name";

        public static final String EMAIL = "email";

        public static final String MOBILE = "mobile";

        public static final String PROFILE_PIC_URL = "profile_pic_url";

        public static final String FAVORITE = "favorite";

        public static final String CONTENT_TYPE = DatabaseUtils.getTableContentType(PATH);

        public static final Uri CONTENT_URI = DatabaseUtils.getTableContentUri(PATH);

        public static final String[] DEFAULT_PROJECTION =
                {_ID, SERVER_ID, FIRST_NAME, LAST_NAME, EMAIL, MOBILE, PROFILE_PIC_URL, FAVORITE};

        public static String create() {
            return create(PATH,
                    new StringBuilder(FIRST_NAME).append(TEXT).append(NOT_NULL)
                            .append(COMMA)
                            .append(LAST_NAME).append(TEXT)
                            .append(COMMA)
                            .append(EMAIL).append(TEXT)
                            .append(COMMA)
                            .append(PROFILE_PIC_URL).append(TEXT)
                            .append(COMMA)
                            .append(SERVER_ID).append(INTEGER).append(NOT_NULL)
                            .append(COMMA)
                            .append(FAVORITE).append(INTEGER).append(DEFAULT)
                            .append(Boolean.FALSE)
                            .append(COMMA)
                            .append(MOBILE).append(TEXT).toString(), UNIQUE + BRACE_OPEN + SERVER_ID +
                            BRACE_CLOSE);
        }

        public static String drop() {
            return drop(PATH);
        }

    }

}
