package com.example.androidnotepadnodejs.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;



public class RetrieveNotes extends AsyncTask<Context, Void, String> {

    public interface AsyncResponse {
        void processFinish(ArrayList<Note> output);
    }

    public AsyncResponse delegate = null;
    private Exception exception;
    private StringBuilder response;
    //private Context context;

    public RetrieveNotes(AsyncResponse delegate){
        this.delegate = delegate;
    }


    protected String doInBackground(Context... params) {
        //context = params[0];
        try {
            HttpRequest req = new HttpRequest("http://10.0.2.2:3000/api/notes");
            response = req.json(HttpRequest.Method.GET);
            Log.d("Profile Json: ", response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("http MalformedURLException", e.getMessage());
        } catch (IOException ex) {
            Log.d("http IOException", ex.getMessage());
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result){
        // TODO: check this.exception
        // TODO: do something with the feed
        Log.d("result", result);
        onTaskCompleted(result);
    }

    public void onTaskCompleted(String response) {
        JsonParse jsonParse = new JsonParse();
        ArrayList<Note> noteArrayList;
        if (jsonParse.isSuccess(response)) {
            //JsonUtils.removeSimpleProgressDialog();  //will remove progress dialog
            noteArrayList = jsonParse.getInfo(response);
            Log.d("noteArrayList",noteArrayList.get(0).note);
            delegate.processFinish(noteArrayList);
        }else {
            //Toast.makeText(context, jsonParse.getErrorCode(response), Toast.LENGTH_SHORT).show();
            Log.d("jsonParse.getErrorCode(response)",jsonParse.getErrorCode(response));
        }
    }
}
