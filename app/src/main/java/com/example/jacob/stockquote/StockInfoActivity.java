package com.example.jacob.stockquote;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class StockInfoActivity extends AppCompatActivity {

    private static final String TAG = "STOCK_QUOTE";

    private TextView nameTextView;
    private TextView yearHighTextView;
    private TextView yearLowTextView;
    private TextView dayHighTextView;
    private TextView dayLowTextView;
    private TextView lastTradePriceOnlyTextView;
    private TextView changeTextView;
    private TextView dayRangeTextView;


    static final String KEY_ITEM = "quote"; // parent node
    static final String KEY_NAME = "Name";
    static final String KEY_YEAR_LOW = "YearLow";
    static final String KEY_YEAR_HIGH = "YearHigh";
    static final String KEY_DAYS_LOW = "DaysLow";
    static final String KEY_DAYS_HIGH = "DaysHigh";
    static final String KEY_LAST_TRADE_PRICE = "LastTradePriceOnly";
    static final String KEY_CHANGE = "Change";
    static final String KEY_DAYS_RANGE = "DaysRange";

    // XML Data to Retrieve
    String name = "";
    String yearLow = "";
    String yearHigh = "";
    String daysLow = "";
    String daysHigh = "";
    String lastTradePriceOnly = "";
    String change = "";
    String daysRange = "";

    String yahooUrlFirst = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22";
    String yahooUrlLast = "%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    String[][] xmlPullParserArray = {{"AverageDailyVolume", "0"},{"Change", "0"},{"DaysLow", "0"},{"DaysHigh", "0"},{"YearLow", "0"},{"YearHigh", "0"},{"MarketCapitalization", "0"},{"LastTradePriceOnly", "0"},{"DaysRange", "0"},
            {"Name", "0"},{"Symbol", "0"},{"Volume", "0"},{"StockExchange", "0"}};

    int parserIndex = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

        nameTextView = (TextView) findViewById(R.id.companyNameTextView);
        yearHighTextView = (TextView) findViewById(R.id.yearHighTextView);
        yearLowTextView = (TextView) findViewById(R.id.yearLowTextView);
        dayHighTextView = (TextView) findViewById(R.id.dayHighTextView);
        dayLowTextView = (TextView) findViewById(R.id.dayLowTextView);
        lastTradePriceOnlyTextView = (TextView) findViewById(R.id.lastTradingPriceOnlyTextView);
        changeTextView = (TextView) findViewById(R.id.changeTextView);
        dayRangeTextView = (TextView) findViewById(R.id.dailyRangeTextView);

        Intent intent = getIntent();
        String stockSymbol = intent.getStringExtra(MainActivity.STOCK_SYMBOL);

        final String yqlUrl = yahooUrlFirst + stockSymbol + yahooUrlLast;

        Log.d(TAG, "Launching Async Task");

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(yqlUrl);
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser pullParser = factory.newPullParser();
                pullParser.setInput(getUrlData(params[0]), null);
                beginDocument(pullParser, "query");
                int eventType = pullParser.getEventType();
                do {
                    nextElement(pullParser);
                    pullParser.next();
                    Log.d(TAG, "Tag: " + pullParser.getName());
                } while (pullParser.getName() == null || !pullParser.getName().equals("quote"));
                do {
                    nextElement(pullParser);
                    pullParser.next();
                    eventType = pullParser.getEventType();

                    if (eventType == XmlPullParser.TEXT) {
                        String valueFromXml = pullParser.getText();
                        xmlPullParserArray[parserIndex++][1] = valueFromXml;
                    }
                } while (eventType != XmlPullParser.END_DOCUMENT);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public InputStream getUrlData(String url) throws IOException {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return  inputStream;
        }

        public final void beginDocument(XmlPullParser parser, String elemName) throws IOException, XmlPullParserException {
            int type;

            while ((type = parser.next()) != parser.START_TAG && type != parser.END_DOCUMENT);

            if (type != parser.START_TAG)
                throw new XmlPullParserException("No Start Tag Found");
            if (!parser.getName().equals(elemName))
                throw new XmlPullParserException("Unexpected Start Tag Found" + parser.getName() + ", Expected " + elemName);
        }

        public final void nextElement(XmlPullParser parser) throws IOException, XmlPullParserException {
            int type;
            while ((type = parser.next()) != parser.START_TAG && type != parser.END_DOCUMENT);
        }

        protected void onPostExecute(String result) {
            nameTextView.setText(xmlPullParserArray[9][1]);
            dayLowTextView.setText(getString(R.string.stock_days_low) + xmlPullParserArray[2][1]);
            dayHighTextView.setText(getString(R.string.stock_days_high) + xmlPullParserArray[3][1]);
            yearLowTextView.setText(getString(R.string.stock_year_low) + xmlPullParserArray[4][1]);
            yearHighTextView.setText(getString(R.string.stock_year_high) + xmlPullParserArray[5][1]);
            changeTextView.setText(getString(R.string.change) + xmlPullParserArray[1][1]);
            dayRangeTextView.setText(getString(R.string.days_range) + xmlPullParserArray[8][1]);
            lastTradePriceOnlyTextView.setText(getString(R.string.last_trade_price_only) + xmlPullParserArray[7][1]);
        }


    }
}
