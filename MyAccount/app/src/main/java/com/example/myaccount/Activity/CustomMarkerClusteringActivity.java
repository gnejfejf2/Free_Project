/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myaccount.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.Person;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.MultiDrawable;
import com.example.myaccount.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Demonstrates heavy customisation of the look of rendered clusters.
 */
public class CustomMarkerClusteringActivity extends MapActivity implements
        ClusterManager.OnClusterClickListener<Person>, ClusterManager.OnClusterInfoWindowClickListener<Person>,
        ClusterManager.OnClusterItemClickListener<Person>, ClusterManager.OnClusterItemInfoWindowClickListener<Person> {
    private ClusterManager<Person> mClusterManager;
    private Random mRandom = new Random(1984);

    /**
     * Draws profile photos inside markers (using IconGenerator).
     * When there are multiple people in the cluster, draw multiple photos (using MultiDrawable).
     */
    private class PersonRenderer extends DefaultClusterRenderer<Person> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final ImageView mImageView;
        private final ImageView mClusterImageView;
        private final int mDimension;

        public PersonRenderer() {
            super(getApplicationContext(), getMap(), mClusterManager);

            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);//멀티프로필에 셋팅
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);//멀티아이템의 이미지

            mImageView = new ImageView(getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);//사진크기조정
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);//사진간격
            mImageView.setPadding(padding, padding, padding, padding);//사진에 간격넣기 4dp씩
            mIconGenerator.setContentView(mImageView);
        }

        @Override
        protected void onBeforeClusterItemRendered(Person person, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.
            mImageView.setImageDrawable(person.profilePhoto);
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(person.name);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (Person p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = p.profilePhoto;
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }

    @Override
    public boolean onClusterClick(Cluster<Person> cluster) {//클릭리스터
        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().name;//이름을 가져온다
        Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();//이름을 토스트 메세지로 보여준다

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Person> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(Person item) {
        // Does nothing, but you could go into the user's profile page, for example.
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Person item) {
        // Does nothing, but you could go into the user's profile page, for example.
    }

    @Override
    protected void startDemo(boolean isRestore) {
        if (!isRestore) {
            SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
            String query = "SELECT  * FROM " + CompanyBoardContract.ComponyBoardEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            db.getPageSize();
            cursor.moveToLast();//7,8 위도경도 5사진 1이름
            if (cursor.getCount() != 0) {

                Intent intent=getIntent();
                String latitude=intent.getStringExtra("latitude");
                String longitude=intent.getStringExtra("longitude");

                LatLng MAINSTORE = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                getMap().moveCamera(CameraUpdateFactory.newLatLng(MAINSTORE));
                getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(MAINSTORE, 17));
            }


        }

        mClusterManager = new ClusterManager<Person>(this, getMap());
        mClusterManager.setRenderer(new PersonRenderer());
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();
    }

    private void addItems() {
        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String query = "SELECT  * FROM " + CompanyBoardContract.ComponyBoardEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        db.getPageSize();
        cursor.moveToLast();//7,8 위도경도 5사진 1이름
        if (cursor.getCount() != 0) {

            String maplatitude = cursor.getString(7);
            String maplongitude = cursor.getString(8);
            String mapphotojson = cursor.getString(5);
            String mapid = cursor.getString(3);
            String maptitle = cursor.getString(1);

            Uri uri = Uri.parse(mapphotojson);
            Bitmap bitmap = null;
            try {
                bitmap = resizeBitmapImg(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            double lat = Double.parseDouble(maplatitude);
            double lng = Double.parseDouble(maplongitude);
            Drawable drawable=new BitmapDrawable(bitmap);


            mClusterManager.addItem(new Person(position(lat,lng), maptitle, drawable));



        }

        while (cursor.moveToPrevious()) {
            String maplatitude = cursor.getString(7);
            String maplongitude = cursor.getString(8);
            String mapphotojson = cursor.getString(5);
            String mapid = cursor.getString(3);
            String maptitle = cursor.getString(1);


            if (maplatitude != null && maplongitude != null) {
                LatLng STORE = new LatLng(Double.parseDouble(maplatitude), Double.parseDouble(maplongitude));
                Uri uri = Uri.parse(mapphotojson);
                Bitmap bitmap = null;
                try {
                    bitmap = resizeBitmapImg(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                double lat = Double.parseDouble(maplatitude);
                double lng = Double.parseDouble(maplongitude);
                Drawable drawable=new BitmapDrawable(bitmap);

                mClusterManager.addItem(new Person(position(lat,lng), maptitle,drawable));

            }
        }
    }

    private LatLng position(double lat,double Ing) {
        return new LatLng(lat,Ing);
    }


    public Bitmap resizeBitmapImg(Bitmap source) {

        int newWidth = 70;
        int newHeight = 140;


        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }
}
