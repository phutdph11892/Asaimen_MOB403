package com.example.asaimen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asaimen.R;
import com.example.asaimen.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LapTopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayLT;

    public LapTopAdapter(Context context, ArrayList<Sanpham> arrayLT) {
        this.context=context;
        this.arrayLT=arrayLT;
    }

    @Override
    public int getCount() {
        return arrayLT.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLT.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        ImageView imageLT;
        TextView txtTenLT,txtGiaLT,txtMotaLT;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.row_listview_laptop,null );
            viewHolder.txtTenLT=view.findViewById( R.id.txtTenspLT );
            viewHolder.txtGiaLT=view.findViewById( R.id.txtGiaspLT );
            viewHolder.txtMotaLT=view.findViewById( R.id.txtMotaspLT );
            viewHolder.imageLT=view.findViewById( R.id.imageLT );
            view.setTag( viewHolder );
        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham)getItem( i );
        viewHolder.txtTenLT.setText( sanpham.getTenSP() );
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaLT.setText( "Gia :"+decimalFormat.format( sanpham.getGiaSP() )+"đ" );
        viewHolder.txtMotaLT.setMaxLines( 2 );
        viewHolder.txtMotaLT.setEllipsize( TextUtils.TruncateAt.END );
        viewHolder.txtMotaLT.setText( sanpham.getMotaSP() );
        Picasso.with( context ).load( sanpham.getHinhanhSP() )
                .placeholder( R.drawable.camera )
                .error( R.drawable.error )
                .into( viewHolder.imageLT );
        return view;
    }
}
