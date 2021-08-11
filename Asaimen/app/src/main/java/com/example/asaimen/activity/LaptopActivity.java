package com.example.asaimen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asaimen.R;
import com.example.asaimen.adapter.LapTopAdapter;
import com.example.asaimen.model.Sanpham;
import com.example.asaimen.ultil.CheckConnection;
import com.example.asaimen.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLT;
    ListView listViewLT;
    LapTopAdapter lapTopAdapter;
    ArrayList<Sanpham> mangLT;
    int idLT=0;
    int page=1;
    View footeview;
    Boolean isLoading=false;
    mHandler mHandler;
    boolean limitData=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_laptop );
        Anhxa();
        if (CheckConnection.haveNetworkConnection( getApplicationContext() )) {
            GetIDloaisp();
            ActionToobar();
            GetData( page );
            LoadMoreData();
        } else {
            CheckConnection.showToast_short( getApplicationContext(), "Ban hay kiem tra lai internet" );
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

    private void LoadMoreData() {
        listViewLT.setOnScrollListener( new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {
                Intent intent=new Intent( getApplicationContext(), ChiTietSPActivity.class );
                intent.putExtra( "thongtinsanpham", mangLT.get( i ) );
                startActivity( intent );
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    isLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        } );
    }

    private void Anhxa() {
        toolbarLT=findViewById( R.id.toolbarLT );
        listViewLT=findViewById( R.id.listviewLT );
        mangLT=new ArrayList<>();
        lapTopAdapter=new LapTopAdapter( getApplicationContext(), mangLT );
        listViewLT.setAdapter( lapTopAdapter );
        LayoutInflater inflater=(LayoutInflater) getSystemService( LAYOUT_INFLATER_SERVICE );
        footeview=inflater.inflate( R.layout.progressbar, null );
        mHandler=new mHandler();
    }

    private void ActionToobar() {
        setSupportActionBar( toolbarLT );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarLT.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    private void GetIDloaisp() {
        idLT=getIntent().getIntExtra( "idloaisanpham", -1 );
//        Log.d( "gia tri san pham",idLT+"" );
    }

    private void GetData(int page) {
        RequestQueue requestQueue=Volley.newRequestQueue( getApplicationContext() );
        String url=Server.Duongdandlaptop + String.valueOf( page );
        StringRequest stringRequest=new StringRequest( Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String TenLT="";
                int GiaLT=0;
                String HinhanhLT="";
                String MotaLT="";
                int IdspLT=0;
                if (response != null && response.length() != 2) {
                    listViewLT.removeFooterView( footeview );
                    try {
                        JSONArray jsonArray=new JSONArray( response );
                        for (int i=0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject=jsonArray.getJSONObject( i );
                            id=jsonObject.getInt( "id" );
                            TenLT=jsonObject.getString( "tensp" );
                            GiaLT=jsonObject.getInt( "giasp" );
                            HinhanhLT=jsonObject.getString( "hinhanhsp" );
                            MotaLT=jsonObject.getString( "motasp" );
                            IdspLT=jsonObject.getInt( "idsanpham" );
                            mangLT.add( new Sanpham( id, TenLT, GiaLT, HinhanhLT, MotaLT, IdspLT ) );
                            lapTopAdapter.notifyDataSetChanged();//cap nhat lai adapter
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    limitData=true;
                    listViewLT.removeFooterView( footeview );
                    CheckConnection.showToast_short( getApplicationContext(), "Het du lieu" );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param=new HashMap<String, String>();
                param.put( "idsanpham", String.valueOf( idLT ) );

                return param;
            }
        };
        requestQueue.add( stringRequest );
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    listViewLT.addFooterView( footeview );
                    break;
                case 1:
                    GetData( ++page );//cong page truoc moi thuc hien
                    isLoading=false;
                    break;

            }
            super.handleMessage( msg );
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage( 0 );
            try {
                Thread.sleep( 3000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHandler.obtainMessage( 1 );
            mHandler.sendMessage( message );
            super.run();
        }
    }
}