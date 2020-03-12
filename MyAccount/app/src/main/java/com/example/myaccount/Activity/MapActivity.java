package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

import com.example.myaccount.Contract.CompanyBoardContract;

import com.example.myaccount.Contract.MyItem;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView map_TextView;
    private boolean mIsRestore;
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mIsRestore = savedInstanceState != null;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mMap != null) {
            return;
        }
        mMap = googleMap;
        startDemo(mIsRestore);


        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String query = "SELECT  * FROM " + CompanyBoardContract.ComponyBoardEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        db.getPageSize();
        cursor.moveToLast();//7,8 위도경도 5사진 1이름
        if (cursor.getCount() != 0) {
            String streetid = cursor.getString(9);


        }


    }
    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    protected abstract void startDemo(boolean isRestore);

    protected GoogleMap getMap() {

        return mMap;
    }


}