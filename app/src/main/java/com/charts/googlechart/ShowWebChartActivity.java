package com.charts.googlechart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by headrun on 19/1/16.
 */
@SuppressLint("SetJavaScriptEnabled")
public class ShowWebChartActivity extends AppCompatActivity {
    WebView webView;
    int num1, num2, num3, num4, num5;

    Spinner spCharts;
    List<String> listCharts;
    List<String> listHtml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webchart);

        webView = (WebView) findViewById(R.id.web);

        webView.addJavascriptInterface(new WebAppInterface(), "Android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setInitialScale(0);

        Intent intent = getIntent();
        num1 = intent.getIntExtra("NUM1", 10);
        num2 = intent.getIntExtra("NUM2", 20);
        num3 = intent.getIntExtra("NUM3", 35);
        num4 = intent.getIntExtra("NUM4", 40);
        num5 = intent.getIntExtra("NUM5", 75);

        spCharts = (Spinner) findViewById(R.id.spcharts);

        listCharts = new ArrayList<String>();
        listCharts.add("stack_colum_chart");
        listCharts.add("Histogram");
        listCharts.add("Pie Chart");
        listCharts.add("Pie Chart 3D");
        listCharts.add("Scatter Chart");
        listCharts.add("Column Chart");
        listCharts.add("Bar Chart");

        listCharts.add("Line Chart");
        listCharts.add("Area Chart");

        listHtml = new ArrayList<String>();
        listHtml.add("stack_colum_chart");
        listHtml.add("histogram");
        listHtml.add("pie_chart");
        listHtml.add("pie_chart_3d");
        listHtml.add("scatter_chart");
        listHtml.add("column_chart");
        listHtml.add("bar_chart");
        listHtml.add("line_chart");
        listHtml.add("area_chart");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCharts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCharts.setAdapter(dataAdapter);

        spCharts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String chartHtml = listHtml.get(parent.getSelectedItemPosition());

                //webView.loadUrl(chartHtml);
                webView.loadDataWithBaseURL("", LoadData(chartHtml), "text/html", "utf-8", null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }

        });

    }

    public String LoadData(String inFile) {
        Log.i("file path is", "" + inFile);
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);
            int size = stream.available();
            Log.i("file size", "" + size);
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
            Log.i("data is", "" + tContents);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

    private int getScale() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int default_display = display.getDisplayId();
        //Double val = new Double(width) / new Double(PIC_WIDTH);
        //val = val * 100d;
        return default_display;
    }

    public class WebAppInterface {

        @JavascriptInterface
        public int getNum1() {
            return num1;
        }

        @JavascriptInterface
        public int getNum2() {
            return num2;
        }

        @JavascriptInterface
        public int getNum3() {
            return num3;
        }

        @JavascriptInterface
        public int getNum4() {
            return num4;
        }

        @JavascriptInterface
        public int getNum5() {
            return num5;
        }
    }

}


