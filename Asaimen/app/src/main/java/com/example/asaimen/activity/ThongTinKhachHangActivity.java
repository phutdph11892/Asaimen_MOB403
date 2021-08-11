package com.example.asaimen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asaimen.R;
import com.example.asaimen.ultil.CheckConnection;
import com.example.asaimen.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    EditText edtTenKhachHang, edtSoDienThoai, edtEmail;
    Button btnXacNhan, btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thong_tin_khach_hang );
        Anhxa();
        btnTroVe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
            EventButton();
        } else {
            CheckConnection.showToast_short( getApplicationContext(), "Ban hay kiem tra lai ket noi" );
        }
    }

    private void EventButton() {
        btnXacNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten=edtTenKhachHang.getText().toString();
                final String sdt=edtSoDienThoai.getText().toString();
                final String email=edtEmail.getText().toString();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0) {
                    RequestQueue queue=Volley.newRequestQueue( getApplicationContext() );
                    StringRequest stringRequest=new StringRequest( Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d( "madonhang", madonhang );


//                        if (Integer.parseInt( madonhang )>0){

                            RequestQueue requestQueue=Volley.newRequestQueue( getApplicationContext() );
                            StringRequest request=new StringRequest( Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    MainActivity.mangGioHang.clear();
                                    Intent intent=new Intent( getApplicationContext(), MainActivity.class );
                                    startActivity( intent );
                                    CheckConnection.showToast_short( getApplicationContext(), "Ban da them do hang thanh cong \n " +
                                            "moi ban tiep tuc mua hang" );

                                    CheckConnection.showToast_short( getApplicationContext(), "gia tri " + response );


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            } ) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray=new JSONArray();
                                    for (int i=0; i < MainActivity.mangGioHang.size(); i++) {
                                        JSONObject jsonObject=new JSONObject();
                                        try {
                                            jsonObject.put( "madonhang", madonhang );
                                            jsonObject.put( "masanpham", MainActivity.mangGioHang.get( i ).getIdSP() );
                                            jsonObject.put( "tensanpham", MainActivity.mangGioHang.get( i ).getTenSP() );
                                            jsonObject.put( "giasanpham", MainActivity.mangGioHang.get( i ).getGiaSP() );
                                            jsonObject.put( "soluongsanpham", MainActivity.mangGioHang.get( i ).getSoLuong() );
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put( jsonObject );
                                        Log.d( "thong tin gio hang", jsonArray.toString() );
                                    }
                                    HashMap<String, String> hashMap=new HashMap<String, String>();
                                    hashMap.put( "json", jsonArray.toString() );
                                    return hashMap;
                                }
                            };
                            requestQueue.add( request );


//                        }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    } ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap=new HashMap<String, String>();
                            hashMap.put( "tenkhachhang", ten );
                            hashMap.put( "sodienthoai", sdt );
                            hashMap.put( "email", email );

                            return hashMap;
                        }
                    };
                    queue.add( stringRequest );
                    Toast.makeText( getApplicationContext(), "Thanh toan thanh cong", Toast.LENGTH_LONG ).show();
                } else {
                    CheckConnection.showToast_short( getApplicationContext(), "Hay kiem tra lai du lieu" );
                }
            }
        } );
    }

    private void Anhxa() {
        edtTenKhachHang=findViewById( R.id.edtTenKhachHang );
        edtEmail=findViewById( R.id.edtEmailKhachHang );
        edtSoDienThoai=findViewById( R.id.edtSoDienThoai );
        btnXacNhan=findViewById( R.id.btnXacNhan );
        btnTroVe=findViewById( R.id.btnTroVe );

    }
}