package edu.rosehulman.trigon.dhucafe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.rosehulman.trigon.dhucafe.items.NewsItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsItem} and makes a call to the
 * specified {@link NewsLIstFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsLIstRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsLIstRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<NewsItem> mValues;
    private final NewsLIstFragment.OnListFragmentInteractionListener mListener;
    private DatabaseReference mDataRef;

    public MyNewsLIstRecyclerViewAdapter(NewsLIstFragment.OnListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
        mDataRef = FirebaseDatabase.getInstance().getReference().child("news");
        mDataRef.addChildEventListener(new NewsChildListener());
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nameView.setText(mValues.get(position).getName());
        holder.detailView.setText(mValues.get(position).getDetail());
        holder.dateView.setText(mValues.get(position).getDate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

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
                holder.imageView.setImageBitmap(drawable);
                super.onPostExecute(drawable);
            }
        }
        new MyTask().execute(mValues.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameView;
        public final TextView detailView;
        public final TextView dateView;
        public final ImageView imageView;
        public NewsItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = (TextView) view.findViewById(R.id.newsName);
            detailView = (TextView) view.findViewById(R.id.newsDetail);
            dateView = (TextView) view.findViewById(R.id.newsDate);
            imageView = (ImageView) view.findViewById(R.id.newsImage);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }

    private class NewsChildListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("firebase changed","add cafe");
            NewsItem item = dataSnapshot.getValue(NewsItem.class);
            mValues.add(0,item);
            notifyItemInserted(0);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.d("firebase changed","update cafe");
            String key = dataSnapshot.getKey();
            NewsItem newitem = dataSnapshot.getValue(NewsItem.class);
            for (NewsItem item:mValues){
                if (item.getKey().equals(key)){
                    item.update(newitem);
                    break;
                }

            }
            notifyDataSetChanged();

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d("firebase changed","remove cafe");
            String key = dataSnapshot.getKey();
            for (NewsItem item:mValues){
                if (item.getKey().equals(key)){
                    mValues.remove(item);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
