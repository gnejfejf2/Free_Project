package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myaccount.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapselectActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button_mapselect,button_mapstore;
    private EditText editText_mapselect;
    private String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapselect);
        button_mapselect = findViewById(R.id.button_mapselect);
        editText_mapselect = findViewById(R.id.editText_mapselect);
        button_mapstore=findViewById(R.id.button_mapstore);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapselectActivity.this);



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;


        geocoder = new Geocoder(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });
        ////////////////////






        //// 검색버튼 이벤트
        button_mapselect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText_mapselect.getText().toString();
                if (str.length()!=0) {
                    List<Address> addressList = null;
                    try {
                        // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                        addressList = geocoder.getFromLocationName(
                                str, // 주소
                                10); // 최대 검색 결과 개수
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList.size() <= 1 && addressList.size() > 0) {
                        System.out.println(addressList.get(0).toString());
                        // 콤마를 기준으로 split
                        String[] splitStr = addressList.get(0).toString().split(",");
                        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소


                        latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                        longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                        System.out.println(latitude);
                        System.out.println(longitude);

                        // 좌표(위도, 경도) 생성
                        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        // 마커 생성
                        MarkerOptions mOptions2 = new MarkerOptions();
                        mOptions2.title("search result");
                        mOptions2.snippet(address);
                        mOptions2.position(point);
                        // 마커 추가
                        mMap.addMarker(mOptions2);
                        // 해당 좌표로 화면 줌
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 17));


                        SharedPreferences map = getSharedPreferences("map", Activity.MODE_PRIVATE);
                        //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                        SharedPreferences.Editor mamStore = map.edit();
                        mamStore.putString("latitude", latitude);
                        mamStore.putString("longitude", longitude);
                        mamStore.putString("streetid",address);
                        mamStore.commit();



                    } else {
                        Toast.makeText(getApplicationContext(), "정확한 위치를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "정확한 위치를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }

        });
        ////////////////////

        // 최초의 위치를 표현해준다
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);//마커에 쓸것

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 17));
        ////////////////////
        button_mapstore.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences map = getSharedPreferences("map", Activity.MODE_PRIVATE);
                //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
                // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
                // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
                String latitude = map.getString("latitude", null);//위도를 가져옴
                String longitude = map.getString("longitude", null);//경도를 가져옴

                Log.d("test12",latitude+"");
                Log.d("test12",longitude+"");

                finish();
            }
        });



    }
}