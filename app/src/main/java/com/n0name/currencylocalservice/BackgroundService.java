package com.n0name.currencylocalservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by N0Name on 20-Feb-16.
 */

//TODO Accessing to DB through DAO
//TODO Content Provider
    

public class BackgroundService extends Service {
    Timer timer;
    ServiceWorker timerTask;
    NotificationManager messagesManager;
    Notification notification;
    int m = 0;
    DatabaseHelper mDbHelper = new DatabaseHelper(this);
    public class CurrencyServiceImpl extends ICurrencyService.Stub
    {
        @Override
        public double getCurrency(String ticker) throws RemoteException
        {
            return 3.86;
        }
    }
    @Override
    public void onCreate() {

        messagesManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        displayMessage("starting Background Service");
        m++;
       // if (m <= 2) {
            timer = new Timer();
            timerTask = new ServiceWorker();
           // Calendar cal = Calendar.getInstance();
            //Date currentLocalTime = cal.getTime();
            //timer.schedule(timerTask, currentLocalTime, 10*1000);
            Log.i("message", "starting " + m);
            Thread t = new Thread(null,
                    new ServiceWorker(), "thread:");
           t.start();
      //  } else displayMessage("already running");
        return START_STICKY;
    }

    class ServiceWorker implements Runnable {
        public void run() {

            InputStream is = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://www.boi.org.il/currency.xml");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                NodeList nameList = doc.getElementsByTagName("NAME");
                NodeList rateList = doc.getElementsByTagName("RATE");
                //NodeList changeList = doc.getElementsByTagName("CHANGE");
                //NodeList countryList = doc.getElementsByTagName("COUNTRY");

                int length = nameList.getLength();

              SQLiteDatabase db = mDbHelper.getWritableDatabase();

               // SQLiteDatabase db=MainActivity.database;
              //  db.delete(DatabaseHelper.TABLE_NAME,null,null);//???
               // db.execSQL("delete from "+ DatabaseHelper.TABLE_NAME);
            // db.execSQL("UPDATE SQLITE_SEQUENCE SET _id=0 WHERE NAME="+DatabaseHelper.TABLE_NAME);


                for (int i = 0; i < length; i++) {
                    Log.i("message", "updating " + i);

                    ContentValues values = new ContentValues();
                    values.put("_id",i);
                    values.put("currency_name", nameList.item(i).getFirstChild().getNodeValue());
                    values.put("value", rateList.item(i).getFirstChild().getNodeValue());
                    // stringBuffer.append(nameList.item(i).getFirstChild().getNodeValue());
                    /*stringList.add(nameList.item(i).getFirstChild().getNodeValue() +
                                    " " + countryList.item(i).getFirstChild().getNodeValue() +
                                    ": " + rateList.item(i).getFirstChild().getNodeValue() +
                                    " (" + changeList.item(i).getFirstChild().getNodeValue() + ")"
                    );*/

                    db.insert(DatabaseHelper.TABLE_NAME,DatabaseHelper.EXCHANGE,values);

                 //   db.execSQL("INSERT INTO "+DatabaseHelper.TABLE_NAME+"("+DatabaseHelper.ROW_ID+
                //           ", "+DatabaseHelper.CURRENCY+", "+DatabaseHelper.EXCHANGE+") VALUES("+
                  //                  i+", "+"dollar"+", "+"5");

                   /* String strFilter = "_id=" + Id;
                    ContentValues args = new ContentValues();
                    args.put(KEY_TITLE, title);
                    myDB.update("titles", args, strFilter, null);*/


                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    con.disconnect();
                }


            }
        }
    }

    @Override
    public void onDestroy() {
        displayMessage("stopping Background Service");
        Log.i("message", "destroying");

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new CurrencyServiceImpl();
    }

    private void displayMessage(String message) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);//?
        Notification.Builder builder = new Notification.Builder(BackgroundService.this);
        builder.setContentTitle("Background Service " + message);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentIntent(contentIntent);
        builder.setTicker(message);
        builder.setContentText("" + message);
        notification = builder.build();
        messagesManager.notify(11, notification);
    }


}
