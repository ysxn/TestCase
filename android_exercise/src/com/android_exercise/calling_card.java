package com.android_exercise;


import android.net.Uri;
import android.provider.BaseColumns;


public final class calling_card {
    public static final String AUTHORITY = "com.android_exercise.calling_card";

    // This class cannot be instantiated
    private calling_card() {}
    
    /**
     * Notes table
     */
    public static final class calling_card_contactors implements BaseColumns {
        // This class cannot be instantiated
        private calling_card_contactors() {}

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/calling_card_contactors");

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.android_exercise.calling_card_contactor";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.android_exercise.calling_card_contactor";

        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";

        /**
         * The title of the note
         * <P>Type: TEXT</P>
         */
        public static final String NAME = "name";

        /**
         * The note itself
         * <P>Type: TEXT</P>
         */
        public static final String JOB = "job";

        /**
         * The note itself
         * <P>Type: TEXT</P>
         */
        public static final String COMPANY = "company";
        
        /**
         * The note itself
         * <P>Type: TEXT</P>
         */
        public static final String MYSELF = "myself";
        
        /**
         * The timestamp for when the note was created
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CREATED_DATE = "created";

        /**
         * The timestamp for when the note was last modified
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String MODIFIED_DATE = "modified";
    }
}