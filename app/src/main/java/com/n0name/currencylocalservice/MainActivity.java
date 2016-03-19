package com.n0name.currencylocalservice;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends Activity {
    CurrDataSource dataSource;
    private Cursor countriesCursor = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        final LinearLayout layout = new LinearLayout(this);
        MoveableProgressBar bar = new MoveableProgressBar(this);
        TextView textView=new TextView(this);

        dataSource = new CurrDataSource(this);



      // textView.setText(""+dataSource.getCurrencyByID(1));
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        bar.setLayoutParams(layoutParams);
        layout.addView(bar);
        layout.addView(textView);
        setContentView(layout);
        startService(new Intent(this,
                BackgroundService.class));




      /*  countriesCursor = database.query(
                DatabaseHelper.TABLE_NAME,
                new String[] {"_id","currency_name","value"},
                null,
                null,
                null,
                null,
                DatabaseHelper.CURRENCY);*/
        dataSource.open();
        //List<Currency> values = dataSource.getAllCurr();
        Cursor cursor=dataSource.getCursor();
        ListAdapter adapter = new SimpleCursorAdapter (
                MainActivity.this,
                R.layout.list_example_entry,
                cursor,
                new String[]{ DatabaseHelper.CURRENCY, DatabaseHelper.EXCHANGE },
                new int[]{ R.id.currency_name, R.id.value });
        ListView listView = new ListView(MainActivity.this);
        textView.setText(""+dataSource.getCurrencyByID(1));
        dataSource.close();

       // ListAdapter adapter = new ArrayAdapter<Currency>(this,android.R.layout.simple_list_item_1, values);


        listView.setAdapter(adapter);
        layout.addView(listView);




        }





}
