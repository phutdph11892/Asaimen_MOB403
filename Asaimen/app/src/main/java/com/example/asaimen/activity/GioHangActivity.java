package com.example.asaimen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asaimen.R;
import com.example.asaimen.adapter.GioHangAdapter;
import com.example.asaimen.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
     ListView listViewGioHang;
    static TextView txtThongBao,txtTongTien;
     Button btnThanhToan,btnTiepTucMua;
     Toolbar toolbarGioHang;
     GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gio_hang );
        Anhxa();
        ActionToobar();
        CheckData();
        EvenUltil();
        CactchonItemListView();
        EvenButton();
    }

    private void EvenButton() {
        btnTiepTucMua.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity( intent );
            }
        } );
        btnThanhToan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGioHang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHangActivity.class);
                    startActivity( intent );
                }else {
                    CheckConnection.showToast_short( getApplicationContext(),"Gio hang cua ban chua co san pham de thanh toan" );
                }
            }
        } );
    }

    private void CactchonItemListView() {
        listViewGioHang.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText( getApplicationContext(),"onclick ",Toast.LENGTH_LONG ).show();
                AlertDialog.Builder builder = new AlertDialog.Builder( GioHangActivity.this );
                builder.setTitle( "Xac nhan xoa san pham" );
                builder.setMessage( "Ban co chac xoa san pham nay" );
                builder.setPositiveButton( "Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.mangGioHang.size()<=0){

                        }else {
                            MainActivity.mangGioHang.remove( position );
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUltil();
                        }
                    }
                } );
                builder.setNegativeButton( "Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUltil();//cap nhat lai tong tien
                    }
                } );
                builder.show();
                return true;
            }
        } );
    }

    public static void EvenUltil() {
        long tongtien = 0;
        for (int i=0;i<MainActivity.mangGioHang.size();i++){
             tongtien+=MainActivity.mangGioHang.get( i ).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText( decimalFormat.format( tongtien )+"d" );

    }

    private void CheckData() {
//        if (MainActivity.mangGioHang.size()<0){
//            gioHangAdapter.notifyDataSetChanged();//cap nhat lai adapter
//            txtThongBao.setVisibility( View.VISIBLE );
//            listViewGioHang.setVisibility( View.VISIBLE );
//        }else {
//            gioHangAdapter.notifyDataSetChanged();//cap nhat lai adapter
//            txtThongBao.setVisibility( View.INVISIBLE );
//            listViewGioHang.setVisibility( View.INVISIBLE );
//        }
    }

    private void ActionToobar() {
        setSupportActionBar( toolbarGioHang );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarGioHang.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    private void Anhxa() {
        listViewGioHang=findViewById( R.id.listviewGiohang );
//        txtThongBao=findViewById( R.id.txtThongbao );
        txtTongTien=findViewById( R.id.txtGiaGioHang );
        btnThanhToan=findViewById( R.id.btnThanhToan );
        btnTiepTucMua=findViewById( R.id.btnTiepTucMuaHang );
        toolbarGioHang=findViewById( R.id.toolbarGioHang );
        gioHangAdapter = new GioHangAdapter( GioHangActivity.this,MainActivity.mangGioHang );
        listViewGioHang.setAdapter( gioHangAdapter );

    }
}