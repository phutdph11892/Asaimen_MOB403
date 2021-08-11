package com.example.asaimen.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asaimen.R;
import com.example.asaimen.model.Giohang;
import com.example.asaimen.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSPActivity extends AppCompatActivity {
        Toolbar toolbarChiTiet;
        ImageView imgChiTiet;
        TextView txtTenCT,txtGiaCT,txtMotaCT;
        Spinner spinner;
        Button btnDatMua;
        int id=0;
        String TenCT ="";
        int GiaCT=0;
        String HinhanhCT = "";
        String MotaCT = "";
        int idSanPham =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chi_tiet_spactivity );
        Anhxa();
        ActionToobar();
        GetInformation();
        CathEvenSpinner();
        EventButoon();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity( intent );
        }
        return super.onOptionsItemSelected( item );
    }
    private void EventButoon() {
        btnDatMua.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGioHang.size()>0){
                int sl= Integer.parseInt( spinner.getSelectedItem().toString() );
                 boolean exists =false;
                for (int i=0;i<MainActivity.mangGioHang.size();i++){
                    if (MainActivity.mangGioHang.get( i ).getIdSP()==id){
                        MainActivity.mangGioHang.get( i ).setSoLuong( MainActivity.mangGioHang.get( i ).getSoLuong()+sl );//cập nhật lại số lượng mới của sản phẩm
                   if (MainActivity.mangGioHang.get( i ).getSoLuong()>=10){
                       MainActivity.mangGioHang.get( i ).setSoLuong( 10 );
                   }
                   MainActivity.mangGioHang.get( i ).setGiaSP( GiaCT*MainActivity.mangGioHang.get( i ).getSoLuong() );
                        exists = true;
                    }
                }
                if (exists==false){
                    int soluong = Integer.parseInt( spinner.getSelectedItem().toString() );
                    long Gia = soluong*GiaCT;
                    MainActivity.mangGioHang.add( new Giohang(id,TenCT,Gia,HinhanhCT,soluong) );
                }
                    Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                    startActivity( intent );
                }else {
                    int soluong = Integer.parseInt( spinner.getSelectedItem().toString() );
                   long Giamoi = soluong*GiaCT;
                   MainActivity.mangGioHang.add( new Giohang(id,TenCT,Giamoi,HinhanhCT,soluong) );
                    Toast.makeText( getApplicationContext(),""+id+"\n"+TenCT+"\n"+Giamoi+"\n"+HinhanhCT+"\n"+soluong ,Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                    startActivity( intent );

                }
            }
        } );
    }

    private void CathEvenSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item,soluong );
        spinner.setAdapter( arrayAdapter );
    }

    private void GetInformation() {

        Sanpham sanpham = (Sanpham)getIntent().getSerializableExtra( "thongtinsanpham" );
        id = sanpham.getId();
        TenCT=sanpham.getTenSP();
        GiaCT=sanpham.getGiaSP();
        HinhanhCT=sanpham.getHinhanhSP();
        MotaCT=sanpham.getMotaSP();
        idSanPham=sanpham.getIDSanpham();
        txtTenCT.setText( TenCT );
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaCT.setText( "Gia :" +decimalFormat.format( GiaCT )+"d");
        txtMotaCT.setText( MotaCT );
        Picasso.with( getApplicationContext() ).load( HinhanhCT )
                .placeholder( R.drawable.camera )
                .error( R.drawable.error )
                .into( imgChiTiet );
    }

    private void ActionToobar() {
        setSupportActionBar( toolbarChiTiet );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarChiTiet.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    private void Anhxa() {
        toolbarChiTiet=findViewById( R.id.toolbarChiTietSP );
        imgChiTiet=findViewById( R.id.imageChitietSP );
        txtTenCT = findViewById( R.id.txtTenChiTietSP );
        txtGiaCT=findViewById( R.id.txtGiaChiTietSP );
        txtMotaCT=findViewById( R.id.txtMotaChiTietSP );
        spinner = findViewById( R.id.spinner );
        btnDatMua = findViewById( R.id.btnDatMua );

    }
}