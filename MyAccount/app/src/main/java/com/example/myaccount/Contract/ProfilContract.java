package com.example.myaccount.Contract;
import android.provider.BaseColumns;

public class ProfilContract {
    private ProfilContract() {

    }
    public static class ProfilEntry implements BaseColumns{
        public static final String TABLE_NAME="Profil";
        public static final String COLUMN_NAME_DAY="Day";
        public static final String COLUMN_NAME_MYPHOTO="myphoto";
        public static final String COLUMN_NAME_GIRLPHOTO="girlphoto";

    }
}
