package com.example.androidnotepadnodejs.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

public class HttpAddNotesAsync extends AsyncTask<String, Void, Integer> {

    public interface AsyncResponse{
        void processFinish(int result);
    }

    private AsyncResponse delegate;
    private Context context;
    private Note note;

    public HttpAddNotesAsync(AsyncResponse delegate, Context context, Note note) {
        this.delegate = delegate;
        this.context = context;
        this.note = note;
    }

    @Override
    protected Integer doInBackground(String... contexts) {
        int response = 0;
        try {
            HttpRequest req = new HttpRequest("http://10.0.2.2:3000/api/note");
            response = req.postData(HttpRequest.Method.POST, note);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("http MalformedURLException", e.getMessage());
        } catch (IOException ex) {
            Log.d("http IOException", ex.getMessage());
        } catch (JSONException ex) {
            ex.printStackTrace();
            Log.d("http JSONException", ex.getMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        delegate.processFinish(result);
    }
}
