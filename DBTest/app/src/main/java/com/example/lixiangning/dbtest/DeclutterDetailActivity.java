package com.example.lixiangning.dbtest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class DeclutterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_detail);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.setTitle("Donation Information");

        DBHelper dbHelper = new DBHelper(this);

        Button declutter = (Button) findViewById(R.id.btn_ready_de);
        declutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = HomeFragment.instance.getDeclutterID();
                String sql = "delete from clothes where clothID="+ id;
                dbHelper.update(sql, new DBHelper.UpdateCallback() {
                    @Override
                    public void onFinished() {
                        if(DeclutterFragment.instance != null) {
                            DeclutterFragment.instance.refresh();
                        }
                        HomeFragment.instance.refresh();
                        Toast.makeText(DeclutterDetailActivity.this, "Declutter Successfully!", Toast.LENGTH_SHORT);
                        finish();
                    }
                });
            }
        });

    }


    /**
     * Action bar button listener
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
