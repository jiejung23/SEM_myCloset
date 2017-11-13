package com.example.junjun.ordercoffee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private DBHelper dbhelper;

    private Button btn_keep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper = new DBHelper(this);
//        dbhelper = new DBHelper(view.getActivity());

        btn_keep = (Button) findViewById(R.id.btn_keep);

        btn_keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView quantityTextView = (TextView) findViewById(R.id.text_noti_1);
                dbhelper.update("update clothes set keepDate=DATE '2017-11-06' where clothID=259;");
                quantityTextView.setText("You kept it! Love this cloth!");
            }
        });


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void keepCloth(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_1);
        dbhelper.update("update clothes set keepDate=DATE '2017-11-06' where clothID=259;");
        quantityTextView.setText("You kept it! Love this cloth!");
    }

    public void deleteCloth(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_1);
        dbhelper.update("update clothes set declutterYes=1 where clothID=259;");
        quantityTextView.setText("It went to trashbox. Also declutter it in your real closet");
    }

    public void thinkCloth(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_1);
        dbhelper.update("update clothes set reminderDate=DATE '2017-12-06' where clothID=259;");
        quantityTextView.setText("Will reminder you 1 month later");
    }

    public void keepCloth2(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_2);
        dbhelper.update("update clothes set keepDate=DATE '2017-11-06' where clothID=258;");
        quantityTextView.setText("You kept it! Love this cloth!");
    }

    public void deleteCloth2(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_2);
        dbhelper.update("update clothes set declutterYes=1 where clothID=258;");
        quantityTextView.setText("It went to trashbox. Also declutter it in your real closet");
    }

    public void thinkCloth2(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_2);
        dbhelper.update("update clothes set reminderDate=DATE '2017-12-06' where clothID=258;");
        quantityTextView.setText("Will reminder you 1 month later");
    }

    public void keepCloth3(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_3);
        dbhelper.update("update clothes set keepDate=DATE '2017-11-06' where clothID=260;");
        quantityTextView.setText("You kept it! Love this cloth!");
    }

    public void deleteCloth3(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_3);
        dbhelper.update("update clothes set declutterYes=1 where clothID=260;");
        quantityTextView.setText("It went to trashbox. Also declutter it in your real closet");
    }

    public void thinkCloth3(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.text_noti_3);
        dbhelper.update("update clothes set reminderDate=DATE '2017-12-06' where clothID=260;");
        quantityTextView.setText("Will reminder you 1 month later");

    }

}