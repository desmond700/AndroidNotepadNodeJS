package com.example.androidnotepadnodejs.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class HttpRetrieveNoteDetailsAsync extends AsyncTask<String, Void, String> {

    public interface AsyncResponse {
        void processFinish(Note output);
    }

    public AsyncResponse delegate = null;
    private String response;
    private Context context;
    private String note_id;

    public HttpRetrieveNoteDetailsAsync(AsyncResponse delegate, Context context, String note_id){
        this.context = context;
        this.delegate = delegate;
        this.note_id = note_id;
    }

    protected String doInBackground(String... params) {
        //context = params[0];
        try {
            HttpRequest req = new HttpRequest(URLConstants.HOST+":"+URLConstants.PORT+"/api/note/"+note_id);

            response = req.json(HttpRequest.Method.GET);
            Log.d("note response",response);
            if(response.equals("CONNECTIONFAILED")){

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("http MalformedURLException", e.getMessage());
        } catch (IOException ex) {
            Log.d("http IOException", ex.getMessage());
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result){
        // TODO: check this.exception
        // TODO: do something with the feed
        Log.d("result", result);
        onTaskCompleted(result);
    }

    public void onTaskCompleted(String response) {
        Log.d("result", response);
        if(!response.equals("CONNECTIONFAILED")) {
            JsonParse jsonParse = new JsonParse();
            Note note;
            if (jsonParse.isSuccess(response)) {
                //JsonUtils.removeSimpleProgressDialog();  //will remove progress dialog
                note = jsonParse.getNote(response);
                Log.d("note", note.note);
                delegate.processFinish(note);
            } else {
                Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("no network connect", "CONNECTFAILED");
            //noConnectionDialog();
        }
    }
}
