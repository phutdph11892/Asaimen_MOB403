package com.example.asaimen.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.asaimen.R;
import com.example.asaimen.adapter.LoaispAdapter;
import com.example.asaimen.adapter.SanPhamAdapter;
import com.example.asaimen.model.Giohang;
import com.example.asaimen.model.Loaisp;
import com.example.asaimen.model.Sanpham;
import com.example.asaimen.ultil.CheckConnection;
import com.example.asaimen.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp="";
    ArrayList<Sanpham> mangSanPham;
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<Giohang> mangGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Anhxa();

        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
            ActionBar();
            ActionViewPager();
            GetDuLieuLoaisp();
            GetDuLieuSanPhamMoiNhat();
            CatchOnItemListView();

        } else {
            CheckConnection.showToast_short( getApplicationContext(), "Ban hau kiem tra lai ket noi" );
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent=new Intent( getApplicationContext(), GioHangActivity.class );
                startActivity( intent );
        }
        return super.onOptionsItemSelected( item );
    }

    private void CatchOnItemListView() {
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
                            Intent intent=new Intent( MainActivity.this, MainActivity.class );
                            startActivity( intent );
                        } else {
                            CheckConnection.showToast_short( getApplicationContext(), "Hay kiem tra lai ket noi" );
                        }
                        drawerLayout.closeDrawer( GravityCompat.START );//dong
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
                            Intent intent=new Intent( MainActivity.this, LienHeActivity.class );
                            startActivity( intent );
                        } else {
                            CheckConnection.showToast_short( getApplicationContext(), "Hay kiem tra lai ket noi" );
                        }
                        drawerLayout.closeDrawer( GravityCompat.START );//dong
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
                            Intent intent=new Intent( MainActivity.this, MapsActivity.class );
                            startActivity( intent );
                        } else {
                            CheckConnection.showToast_short( getApplicationContext(), "Hay kiem tra lai ket noi" );
                        }
                        drawerLayout.closeDrawer( GravityCompat.START );//dong
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
                            Intent intent=new Intent( MainActivity.this, DienThoaiActivity.class );
                            intent.putExtra( "idloaisanpham", mangloaisp.get( i - 2 ).getId() );
//                            Toast.makeText( getApplicationContext(),"id san pham"+(i-2),Toast.LENGTH_LONG ).show();
                            startActivity( intent );
                        } else {
                            CheckConnection.showToast_short( getApplicationContext(), "Hay kiem tra lai ket noi" );
                        }
                        drawerLayout.closeDrawer( GravityCompat.START );//dong
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
                            Intent intent=new Intent( MainActivity.this, LaptopActivity.class );
                            intent.putExtra( "idloaisanpham", mangloaisp.get( i - 2 ).getId() );
//                            Toast.makeText( getApplicationContext(),"id san pham"+(i-2),Toast.LENGTH_LONG ).show();
                            startActivity( intent );
                        } else {
                            CheckConnection.showToast_short( getApplicationContext(), "Hay kiem tra lai ket noi" );
                        }
                        drawerLayout.closeDrawer( GravityCompat.START );//dong
                        break;
                }

            }
        } );
    }


    private void GetDuLieuSanPhamMoiNhat() {
        RequestQueue requestQueue=Volley.newRequestQueue( getApplicationContext() );
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest( Server.Duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID=0;
                    String TenSP="";
                    Integer GiaSP=0;
                    String HinhanhSP="";
                    String MotaSP="";
                    int IDsanpham=0;
                    for (int i=0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject=response.getJSONObject( i );
                            ID=jsonObject.getInt( "id" );
                            TenSP=jsonObject.getString( "tensp" );
                            GiaSP=jsonObject.getInt( "giasp" );
                            HinhanhSP=jsonObject.getString( "hinhanhsp" );
                            MotaSP=jsonObject.getString( "motasp" );
                            IDsanpham=jsonObject.getInt( "idsanpham" );
                            mangSanPham.add( new Sanpham( ID, TenSP, GiaSP, HinhanhSP, MotaSP, IDsanpham ) );
                            sanPhamAdapter.notifyDataSetChanged();//goi lai de load san pham
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_short( getApplicationContext(), error.toString() );
            }
        } );
        requestQueue.add( jsonArrayRequest );
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue=Volley.newRequestQueue( getApplicationContext() );
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest( Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i=0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject=response.getJSONObject( i );
                            id=jsonObject.getInt( "id" );
                            tenloaisp=jsonObject.getString( "tenloaisp" );
                            hinhanhloaisp=jsonObject.getString( "hinhanhloaisp" );
                            mangloaisp.add( new Loaisp( id, tenloaisp, hinhanhloaisp ) );
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_short( getApplicationContext(), error.toString() );
            }
        } );
        requestQueue.add( jsonArrayRequest );
    }

    private void ActionViewPager() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add( "https://photo2.tinhte.vn/data/attachment-files/2020/10/5184803_Screen_Shot_2020-10-14_at_00.47.42.jpg" );
        mangquangcao.add( "https://genk.mediacdn.vn/2019/8/1/photo-1-1564625315375793292194.jpg" );
        mangquangcao.add( "https://media.congluan.vn/files/dinhtrung/2020/09/24/iphone-12-1352.jpg" );
        mangquangcao.add( "https://genk.mediacdn.vn/2019/8/1/photo-1-1564625315375793292194.jpg" );
        for (int i=0; i < mangquangcao.size(); i++) {
            ImageView imageView=new ImageView( getApplicationContext() );
            Picasso.with( getApplicationContext() ).load( mangquangcao.get( i ) ).into( imageView );
            imageView.setScaleType( ImageView.ScaleType.FIT_XY );
            viewFlipper.addView( imageView );
        }
        viewFlipper.setFlipInterval( 5000 );
        viewFlipper.setAutoStart( true );
        Animation animation_slide_in=AnimationUtils.loadAnimation( getApplicationContext(), R.anim.slide_in_right );
        Animation animation_slide_out=AnimationUtils.loadAnimation( getApplicationContext(), R.anim.slider_out_right );
        viewFlipper.setInAnimation( animation_slide_in );
        viewFlipper.setOutAnimation( animation_slide_out );


    }

    private void ActionBar() {
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationIcon( android.R.drawable.ic_menu_sort_by_size );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer( GravityCompat.START );
            }
        } );
    }

    private void Anhxa() {
        toolbar=findViewById( R.id.toolbarManChinh );
        viewFlipper=findViewById( R.id.viewFlipperManChinh );
        recyclerView=findViewById( R.id.recyclerviewManhChinh );
        navigationView=findViewById( R.id.navigationviewManChinh );
        listView=findViewById( R.id.listviewManChinh );
        drawerLayout=findViewById( R.id.drawerlayoutManChinh );
        mangloaisp=new ArrayList<>();
        mangloaisp.add( 0, new Loaisp( 0, "Trang Chính", "https://vietadsgroup.vn/Uploads/files/trangchu-la-gi.png" ) );
        mangloaisp.add( 1, new Loaisp( 0, "Liên Hệ", "https://danviet.mediacdn.vn/upload/3-2014/images/2014-09-30/1434399355-wmbzduong_day_nong_fsbf.jpg" ) );
        mangloaisp.add( 2, new Loaisp( 0, "Thông Tin", "https://cdn4.vectorstock.com/i/1000x1000/78/38/happy-man-sitting-at-his-desk-with-laptop-vector-21167838.jpg" ) );
        loaispAdapter=new LoaispAdapter( mangloaisp, getApplicationContext() );
        listView.setAdapter( loaispAdapter );
        mangSanPham=new ArrayList<>();
        sanPhamAdapter=new SanPhamAdapter( getApplicationContext(), mangSanPham );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new GridLayoutManager( getApplicationContext(), 2 ) );
        recyclerView.setAdapter( sanPhamAdapter );
        if (mangGioHang != null) {

        } else {
            mangGioHang=new ArrayList<>();
        }
    }

}