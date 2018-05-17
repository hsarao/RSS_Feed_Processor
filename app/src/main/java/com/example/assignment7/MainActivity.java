package com.example.assignment7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private ListView listView;
    private SharedPreferences prefs;
    private ProgressDialog progressDialog = null;
    private newsAdapter newsAdapter;
    private boolean isWinnipegPress;
    private URL url;
    private float fontSize = 16;
    int i = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Loading....", Toast.LENGTH_SHORT).show();
        ProcessRSSTask processRSSTask = new ProcessRSSTask();
        processRSSTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean source = false;

        if( id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }
        if( id == R.id.refresh)
        {
           // prefs = getSharedPreferences("font_size",MODE_PRIVATE);
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            float font = Float.valueOf(prefs.getString("font_size", "testing"));
            Toast.makeText(MainActivity.this, "test"+font, Toast.LENGTH_LONG).show();
            this.recreate();
        }
        if( id == R.id.source)
        {
            prefs = getSharedPreferences("url",MODE_PRIVATE);
            i++;

            String url = prefs.getString("url", "no url found in prefs");
            SharedPreferences.Editor editor = prefs.edit();
            //---save the values in the EditText view to preferences---

                editor.putString("url", "https://talksport.com/rss/sports-news/football/feed");

               editor.putString("url", "https://www.winnipegfreepress.com/rss/?path=%2Fsports%2Fsoccer");


            editor.commit();
            Toast.makeText(MainActivity.this, prefs.getString("url", "testing"), Toast.LENGTH_LONG).show();
            source = true;
            this.recreate();

        }
        if( id == R.id.source2)
        {
            prefs = getSharedPreferences("url",MODE_PRIVATE);
            i++;

            String url = prefs.getString("url", "no url found in prefs");
            SharedPreferences.Editor editor = prefs.edit();
            //---save the values in the EditText view to preferences---

            editor.putString("url", "https://talksport.com/rss/sports-news/football/feed");
            editor.commit();
            Toast.makeText(MainActivity.this, prefs.getString("font_size", "testing"), Toast.LENGTH_LONG).show();
            source = true;
            this.recreate();

        }
//        if( id == R.id.fontSize)
//        {
//            try {
//                prefs = getSharedPreferences("font_size",MODE_PRIVATE);
//                i++;
//
//                String url = prefs.getString("font_size", "no url found in prefs");
////                SharedPreferences.Editor editor = prefs.edit();
////                //---save the values in the EditText view to preferences---
////
////                editor.putFloat("font_size", 16);
////                editor.commit();
//                fontSize = getFontSize();
//                Toast.makeText(MainActivity.this, prefs.getString("url", "testing"), Toast.LENGTH_LONG).show();
//                source = true;
//                this.recreate();
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
//
//
//        }
        return source;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("font_size")){
            fontSize = Float.parseFloat(sharedPreferences.getString(s, "16"));
            Toast.makeText(MainActivity.this, "testuiophjkl", Toast.LENGTH_SHORT).show();
        }
    }

    class ProcessRSSTask extends AsyncTask<Void, Void, Void> {
        //create instance of our FreepHandler
       private FreepHandler freepHandler = new FreepHandler();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Jody", "onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("Jody", "doInBackground");

            URL url = null;
            try {
//                if(onOptionsItemSelected( )) {
//                    url = new URL("https://talksport.com/rss/sports-news/football/feed");
//                }
//                else
//                {
                    url = new URL(getUrl());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            SAXParser saxParser = null;
            try {
                saxParser = SAXParserFactory.newInstance().newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }



            try {
                saxParser.parse(inputStream, freepHandler);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Jody", "onPostExecute");
            Toast.makeText(MainActivity.this, "test:", Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    listView = findViewById(R.id.nice_listview);
                    newsAdapter = new newsAdapter(MainActivity.this, R.layout.list_item, freepHandler.news);
                    listView.setAdapter(newsAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //we could start an activity to display details of the selected item
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                            intent.putExtra("selectedItem", i);
                            News news = (News) adapterView.getItemAtPosition(i);
                            intent.putExtra("test", news.link);
                           startActivity(intent);

                        }
                    });
                }
            });
        }


    }

    //Subclass of DefaultHandler to parse Wpg Free Press
    //RSS feeds
    class FreepHandler extends DefaultHandler {

        //flags to keep track of what elements we are in
        private boolean inItem, inTitle, inPubDate, inDesc, inLink;

        private List<String> title;
        private List<String> description;
        private List<String> pubDate;
        private List<String> link;
        private List<News> news;
        private StringBuilder stringBuilder;

        //better to have an arraylist of item element data
        //private ArrayList<FeedItem> feedItem;
        //initialization block
        {
            title = new ArrayList<String>(10);

            description = new ArrayList<String>(10);

            news = new ArrayList<News>(10);

            pubDate = new ArrayList<String>(10);
            link = new ArrayList<String>(10);
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            Log.d("Jody", "startDoc");
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            Log.d("Jody", "endDoc");

            //let's see what's in our collections
            Log.d("Jody", "DATA IN ARRAYLISTS:");
            for(String s: title) {
                Log.d("Jody", s);
            }

        for (int i = 0; i < title.size() - 1; i++)
        {
            news.add(new News(title.get(i), description.get(i), pubDate.get(i), link.get(i)));
        }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            Log.d("Jody", "startElement: " + qName);
            if(qName.equals("item"))
            {
                inItem = true;
            }
            if(qName.equals("title")) {
                inTitle = true;
                //set the capacity to be the largest possible number
                // of characters in that element
                stringBuilder = new StringBuilder(30);
            }
            if(qName.equals("description")) {
                inDesc = true;
                //set the capacity to be the largest possible number
                // of characters in that element
                stringBuilder = new StringBuilder(100);
            }
            if(qName.equals("pubDate")) {
                inPubDate = true;
                //set the capacity to be the largest possible number
                // of characters in that element
                stringBuilder = new StringBuilder(50);
            }
            if(qName.equals("link")) {
                inLink = true;
                //set the capacity to be the largest possible number
                // of characters in that element
                stringBuilder = new StringBuilder(30);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            Log.d("Jody", "endElement: " + qName);
            if(qName.equals("item"))
            {
                inItem = false;
            }

            if(qName.equals("title") && inItem) {
                inTitle = false;
                title.add(stringBuilder.toString());
            }
            if(qName.equals("description")&& inItem) {
                inDesc = false;
                description.add(stringBuilder.toString());
            }
            if(qName.equals("pubDate")&& inItem) {
                inPubDate = false;
                pubDate.add(stringBuilder.toString());
            }
            if(qName.equals("link")&& inItem) {
                inLink = false;
                link.add(stringBuilder.toString());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if(inTitle) {
                stringBuilder.append(ch, start, length);
            }
            if(inDesc) {
                stringBuilder.append(ch, start, length);
            }
            if(inPubDate) {
                stringBuilder.append(ch, start, length);
            }
            if(inLink) {
                stringBuilder.append(ch, start, length);
            }
        }
    }
    private class newsAdapter extends ArrayAdapter<News>
    {
      private List<News> title;

        newsAdapter(Context context, int textViewResourceId, List<News> title2) {
            super(context, textViewResourceId, title2);
            this.title = title2;

        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }

            News singleTitle = getItem(position);
          //  String singleDesc = getItem(position);
            if (singleTitle != null) {
                float topFontSize = getFontSize();
                TextView tt = v.findViewById(R.id.toptext);
                tt.setText(singleTitle.getTitle());
                tt.setTextColor(Color.BLUE);
                tt.setTextSize(topFontSize);
                TextView bt = v.findViewById(R.id.bottomtext);
                bt.setText(singleTitle.getDescription());
                bt.setTextSize(getFontSize());
                TextView pubDate = v.findViewById(R.id.pubDate);
                pubDate.setText(singleTitle.getPubDate());
   }
          return v;
        }}
       class News {
                private String title;
                private String description;
                private String pubDate;
                private String link;

                public News(String title, String description, String pubDate, String link) {
                    this.title = title;
                    this.description = description;
                    this.pubDate = pubDate;
                    this.link = link;
                }

                public String getDescription() {
                    return description;
                }
                public String getTitle() {
                    return title;
                }
                public String getPubDate() {
                    return pubDate;
                }
                public String getLink(){return link;}
    }

    private String getUrl (){
        android.content.SharedPreferences mSharedPreferences = getSharedPreferences("url",MODE_PRIVATE);
        String url = mSharedPreferences.getString("url", "https://www.winnipegfreepress.com/rss/?path=%2Fsports%2Fsoccer");
        return url;
    }

    private float getFontSize () {
        return 16;
    }
// Jody's remark - To remove the feed title from the top; Check both if inTitle and iItem then add it to the news object

}