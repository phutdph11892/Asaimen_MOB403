package com.example.asaimen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asaimen.R;
import com.example.asaimen.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDT;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arrayDT) {
        this.context=context;
        this.arrayDT=arrayDT;
    }

    @Override
    public int getCount() {
        return arrayDT.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDT.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
     ImageView imageDT;
     TextView txtTenDT,txtGiaDT,txtMotaDT;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.row_listview_dienthoai,null );
            viewHolder.txtTenDT=view.findViewById( R.id.txtTenspDT );
            viewHolder.txtGiaDT=view.findViewById( R.id.txtGiaspDT );
            viewHolder.txtMotaDT=view.findViewById( R.id.txtMotaspDT );
            viewHolder.imageDT=view.findViewById( R.id.imageDT );
            view.setTag( viewHolder );
        }else {
            viewHolder =(ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham)getItem( i );
        viewHolder.txtTenDT.setText( sanpham.getTenSP() );
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDT.setText( "Gia :"+decimalFormat.format( sanpham.getGiaSP() )+"đ" );
        viewHolder.txtMotaDT.setMaxLines( 2 );
        viewHolder.txtMotaDT.setEllipsize( TextUtils.TruncateAt.END );
        viewHolder.txtMotaDT.setText( sanpham.getMotaSP() );
        Picasso.with( context ).load( sanpham.getHinhanhSP() )
                .placeholder( R.drawable.camera )
                .error( R.drawable.error )
                .into( viewHolder.imageDT );
        return view;
    }
}
