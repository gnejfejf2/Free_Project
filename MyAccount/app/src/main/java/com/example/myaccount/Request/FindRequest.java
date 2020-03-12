package com.example.myaccount.Request;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindRequest extends StringRequest {

    // 서버 URL 설정 php파일 연동
    final static private String URL = "http://gnejfejf2.dothome.co.kr/Find.php";
    private Map<String, String> map;

    public FindRequest(String userID, String userName, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userName",userName);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
