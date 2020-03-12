package com.example.myaccount.Contract;
import android.provider.BaseColumns;

public class Shoppingbasketcontract {
    private Shoppingbasketcontract() {

    }
    public static class Shoppingbasketentry implements BaseColumns{
        public static final String TABLE_NAME="shoppingbasket";
        public static final String COLUMN_NAME_PRICE="price";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_BUYUSERID="buyuserid";
        public static final String COLUMN_NAME_SELLUSERID="selluserid";
        public static final String COLUMN_NAME_PHOTOJSON="photojson";
        public static final String COLUMN_NAME_ISSELECTED="isselected ";
        public static final String COLUMN_NAME_BUYNUMBER="buynumber";
    }
}
