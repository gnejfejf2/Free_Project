package com.example.myaccount.Contract;
import android.provider.BaseColumns;

public class ShoppingContract {
    private ShoppingContract() {

    }
    public static class ShoppingEntry implements BaseColumns{
        public static final String TABLE_NAME="ShoppingBoard";
        public static final String COLUMN_NAME_BUYID="buyid";
        public static final String COLUMN_NAME_ITEMTITLE="itemtitle";
        public static final String COLUMN_NAME_ITEMPRICE="itemprice";
        public static final String COLUMN_NAME_ITEMCOUNTER="itemcounter";
    }
}
