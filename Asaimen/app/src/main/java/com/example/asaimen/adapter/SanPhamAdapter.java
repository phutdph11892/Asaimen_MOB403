package com.example.asaimen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asaimen.R;
import com.example.asaimen.activity.ChiTietSPActivity;
import com.example.asaimen.model.Sanpham;
import com.example.asaimen.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraySanPham;

    public SanPhamAdapter(Context context, ArrayList<Sanpham> arraySanPham) {
        this.context=context;
        this.arraySanPham=arraySanPham;
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view =LayoutInflater.from( parent.getContext() ).inflate( R.layout.row_recy_sanphammoi,null );
        ItemHolder itemHolder = new ItemHolder( view );

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
         Sanpham sanpham = arraySanPham.get( position );
         holder.txtTenSP.setText( sanpham.getTenSP() );
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText( "Gia :"+decimalFormat.format( sanpham.getGiaSP() )+"đ" );
        Picasso.with( context ).load( sanpham.getHinhanhSP() )
                .placeholder( R.drawable.camera )
                .error( R.drawable.error )
                .into( holder.imgHinhSP );
    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhSP;
        public TextView txtTenSP,txtGiaSP;

        public ItemHolder( View itemView) {
            super( itemView );
            imgHinhSP = itemView.findViewById( R.id.imageSP );
            txtGiaSP = itemView.findViewById( R.id.txtGiaSP );
            txtTenSP = itemView.findViewById( R.id.txtTenSP );
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSPActivity.class );
                    intent.putExtra( "thongtinsanpham",arraySanPham.get( getAdapterPosition()) );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    CheckConnection.showToast_short( context,arraySanPham.get( getAdapterPosition() ).getTenSP() );
                    context.startActivity( intent );
                }
            } );
        }
    }
}
