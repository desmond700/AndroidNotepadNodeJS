package com.example.androidnotepadnodejs.util;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.MalformedURLException;

public class HttpDeleteNoteAsync extends AsyncTask<String, Void, Integer> {

    public interface AsyncResponse {
        void processFinish(int output);
    }

    public AsyncResponse delegate = null;
    private int response;
    private String note_id;

    public HttpDeleteNoteAsync(AsyncResponse delegate, String note_id){
        this.delegate = delegate;
        this.note_id = note_id;
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            HttpRequest req = new HttpRequest("http://10.0.2.2:3000/api/note/"+note_id);

            response = req.deleteData(HttpRequest.Method.DELETE);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("http MalformedURLException", e.getMessage());
        } catch (IOException ex) {
            Log.d("http IOException", ex.getMessage());
        }
        return response;
    }

    @Override
    protected void onPostExecute(Integer result){
        // TODO: check this.exception
        // TODO: do something with the feed
        Log.d("result", Integer.toString(result));
        delegate.processFinish(result);
    }
}
