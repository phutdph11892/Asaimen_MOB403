package com.example.asaimen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asaimen.R;
import com.example.asaimen.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp>  arrayListloaisp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListloaisp, Context context) {
        this.arrayListloaisp=arrayListloaisp;
        this.context=context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisp.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
      TextView txtLoaisp;
      ImageView imgLoaisp;

    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.row_listview_loaisp,null );
            viewHolder.txtLoaisp = view.findViewById( R.id.txtLoaisp );
            viewHolder.imgLoaisp = view.findViewById( R.id.imageLoaisp );
            view.setTag( viewHolder );
        }else{
            viewHolder =(ViewHolder) view.getTag();

        }
        Loaisp loaisp =(Loaisp) getItem( i );
        viewHolder.txtLoaisp.setText( loaisp.getTenloaisp() );
        Picasso.with( context ).load( loaisp.getHinhanhloaisp() )
                .placeholder( R.drawable.camera )
                .error( R.drawable.error )
                .into( viewHolder.imgLoaisp );
        return view ;
    }
}
