package com.example.asaimen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asaimen.R;
import com.example.asaimen.activity.GioHangActivity;
import com.example.asaimen.activity.MainActivity;
import com.example.asaimen.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrGioHang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrGioHang) {
        this.context=context;
        this.arrGioHang=arrGioHang;
    }

    @Override
    public int getCount() {
        return arrGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrGioHang.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
     TextView txtTenGioHang,txtGiaGioHang;
     ImageView imgGioHang;
     Button btnminus,btnvalues,btnplus;
    }
    ViewHolder viewHolder = null;
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.row_listview_giohang,null );
            viewHolder.txtTenGioHang = view.findViewById( R.id.txtTenGioHang );
            viewHolder.txtGiaGioHang =view.findViewById( R.id.txtGiaHang );
            viewHolder.imgGioHang = view.findViewById( R.id.imageGioHang );
            viewHolder.btnminus=view.findViewById( R.id.btnminus );
            viewHolder.btnvalues=view.findViewById( R.id.btnvalues );
            viewHolder.btnplus=view.findViewById( R.id.btnplus );
            view.setTag( viewHolder );
        }else {
               viewHolder=(ViewHolder) view.getTag();
        }
       Giohang giohang = (Giohang) getItem( i );
        viewHolder.txtTenGioHang.setText( giohang.getTenSP() );
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText( decimalFormat.format( giohang.getGiaSP() )+"d" );
        Picasso.with( context ).load( giohang.getHinhSP() )
                .placeholder( R.drawable.camera )
                .error( R.drawable.error )
                .into( viewHolder.imgGioHang );
        viewHolder.btnvalues.setText( giohang.getSoLuong() +"");
        int sl = Integer.parseInt( viewHolder.btnvalues.getText().toString() );
        if (sl>10){
            viewHolder.btnplus.setVisibility( View.INVISIBLE );
            viewHolder.btnminus.setVisibility( View.VISIBLE );

        }else if (sl<=1){
              viewHolder.btnminus.setVisibility( View.INVISIBLE );

        }else if (sl>=1){
            viewHolder.btnminus.setVisibility( View.VISIBLE );
            viewHolder.btnplus.setVisibility( View.VISIBLE );
        }

        viewHolder.btnplus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt( viewHolder.btnvalues.getText().toString() )+1;
                int slht =MainActivity.mangGioHang.get( i ).getSoLuong();
                long giaht = MainActivity.mangGioHang.get( i ).getGiaSP();
                MainActivity.mangGioHang.get( i ).setSoLuong( slmoinhat );
                long giaMoiNhat = (giaht*slmoinhat)/slht;
                MainActivity.mangGioHang.get( i ).setGiaSP( giaMoiNhat );
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtGiaGioHang.setText( decimalFormat.format(giaMoiNhat)+"d" );
                GioHangActivity.EvenUltil();
                if (slmoinhat>9){
                    viewHolder.btnplus.setVisibility( View.INVISIBLE );
                    viewHolder.btnminus.setVisibility( View.VISIBLE );
                    viewHolder.btnvalues.setText( String.valueOf( slmoinhat ) );
                }else {
                    viewHolder.btnplus.setVisibility( View.VISIBLE );
                    viewHolder.btnminus.setVisibility( View.VISIBLE );
                    viewHolder.btnvalues.setText( String.valueOf( slmoinhat ) );
                }
            }
        } );
        viewHolder.btnminus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt( viewHolder.btnvalues.getText().toString() )-1;
                int slht =MainActivity.mangGioHang.get( i ).getSoLuong();
                long giaht = MainActivity.mangGioHang.get( i ).getGiaSP();
                MainActivity.mangGioHang.get( i ).setSoLuong( slmoinhat );
                long giaMoiNhat = (giaht*slmoinhat)/slht;
                MainActivity.mangGioHang.get( i ).setGiaSP( giaMoiNhat );
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtGiaGioHang.setText( decimalFormat.format(giaMoiNhat)+"d" );
                GioHangActivity.EvenUltil();
                if (slmoinhat<2){
                    viewHolder.btnplus.setVisibility( View.VISIBLE );
                    viewHolder.btnminus.setVisibility( View.INVISIBLE );
                    viewHolder.btnvalues.setText( String.valueOf( slmoinhat ) );
                }else {
                    viewHolder.btnplus.setVisibility( View.VISIBLE );
                    viewHolder.btnminus.setVisibility( View.VISIBLE );
                    viewHolder.btnvalues.setText( String.valueOf( slmoinhat ) );
                }
            }
        } );
        return view;
    }
}
