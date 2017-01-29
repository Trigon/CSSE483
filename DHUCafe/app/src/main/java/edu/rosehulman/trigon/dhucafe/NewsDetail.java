package edu.rosehulman.trigon.dhucafe;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import edu.rosehulman.trigon.dhucafe.items.NewsItem;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";

    // TODO: Rename and change types of parameters
    private String name;
    private String date;
    private String link;
    private String detail;


    public NewsDetail() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewsDetail newInstance(NewsItem item) {
        Log.d("newInstnce",item.getName());
        NewsDetail fragment = new NewsDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, item.getName());
        args.putString(ARG_PARAM2, item.getDate());
        args.putString(ARG_PARAM3, item.getLink());
        args.putString(ARG_PARAM4, item.getDetail());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate","1");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            date = getArguments().getString(ARG_PARAM2);
            link = getArguments().getString(ARG_PARAM3);
            detail = getArguments().getString(ARG_PARAM4);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","123");
        // Inflate the layout for this fragment
        View  view =inflater.inflate(R.layout.fragment_news_detail, container, false);
        TextView nameView = (TextView)view.findViewById(R.id.newsName);
        TextView dateView = (TextView)view.findViewById(R.id.newsDate);
        TextView detailVIew = (TextView) view.findViewById(R.id.newsDetail);
        final ImageView imageView = (ImageView) view.findViewById(R.id.dimage);
        nameView.setText(name);
        dateView.setText(date);
        detailVIew.setText(detail);
        class MyTask extends AsyncTask<String,Void,Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                InputStream in = null;
                try {
                    in = new URL(strings[0]).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;

                // Bitmap bmp = getURLimage(url);
            }

            @Override
            protected void onPostExecute(Bitmap drawable) {
                imageView.setImageBitmap(drawable);
                super.onPostExecute(drawable);
            }
        }
        new MyTask().execute(link);
        return view;
    }

}
