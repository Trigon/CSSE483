package edu.rosehulman.trigon.dhucafe;

import android.os.AsyncTask;

/**
 * Created by Trigon on 1/13/2017.
 */

public class GetInfoTask extends AsyncTask<String,Void,Info> {

    private TaskConsumer mTaskConsumer;

    public GetInfoTask(TaskConsumer item){
        mTaskConsumer = item;
    }
    @Override
    protected Info doInBackground(String... strings) {
        String urlString = strings[0];
        Info info = null;
        return null;
    }

    @Override
    protected void onPostExecute(Info info) {
        super.onPostExecute(info);
        mTaskConsumer.onTaskLoaded(info);
    }

    public interface TaskConsumer{
        public void onTaskLoaded(Info info);
    }
}
