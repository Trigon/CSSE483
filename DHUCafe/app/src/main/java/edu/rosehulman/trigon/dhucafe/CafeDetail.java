package edu.rosehulman.trigon.dhucafe;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import edu.rosehulman.trigon.dhucafe.items.CafeItem;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CafeDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CafeDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";




    // TODO: Rename and change types of parameters
    private CafeItem item;
    private String name;
    private String detail;
    private String link;
    private int price;



    public CafeDetail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CafeDetail newInstance(CafeItem item) {
        CafeDetail fragment = new CafeDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, item.getName());
        args.putString(ARG_PARAM2, item.getDetail());
        args.putString(ARG_PARAM3, item.getLink());
        args.putInt(ARG_PARAM4,item.getPrice());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate","1");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            detail = getArguments().getString(ARG_PARAM2);
            link = getArguments().getString(ARG_PARAM3);
            price = getArguments().getInt(ARG_PARAM4);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","123");
        // Inflate the layout for this fragment
        View  view =inflater.inflate(R.layout.fragment_cafe_detail, container, false);
        TextView titleText = (TextView)view.findViewById(R.id.title);
        TextView detailText =(TextView)view.findViewById(R.id.cafedetail);
        TextView priceText = (TextView) view.findViewById(R.id.cafeprice);
        final ImageView cafeImage = (ImageView) view.findViewById(R.id.dimage);
        titleText.setText(name);
        detailText.setText(detail);
        priceText.setText(price+"");
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
                cafeImage.setImageBitmap(drawable);
                super.onPostExecute(drawable);
            }
        }
        new MyTask().execute(link);
        return view;
    }

}
