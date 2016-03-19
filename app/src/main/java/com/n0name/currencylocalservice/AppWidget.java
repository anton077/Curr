package com.n0name.currencylocalservice;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Created by N0Name on 1-Mar-16.
 */
public class AppWidget extends AppWidgetProvider {



    CurrDataSource dataSource ;



    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgets) {

        dataSource=new CurrDataSource(context);
        dataSource.open();
        double currency = dataSource.getCurrencyByID(1);
        dataSource.close();

        int numOfWidgets = widgets.length;
        for (int i = 0; i < numOfWidgets; i++) {
            int widget = widgets[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setTextViewText(R.id.textView, "" + currency);
            appWidgetManager.updateAppWidget(widget, views);//appwidget manager is container for android remote services on home screen activity that updates widget
        }
    }


}
