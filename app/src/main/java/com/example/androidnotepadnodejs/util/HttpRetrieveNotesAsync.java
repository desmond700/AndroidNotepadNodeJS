package com.example.androidnotepadnodejs.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.androidnotepadnodejs.ItemListActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;



public class HttpRetrieveNotesAsync extends AsyncTask<String, Void, String> {

    public interface AsyncResponse {
        void processFinish(ArrayList<Note> output);
    }

    public AsyncResponse delegate = null;
    private String response;
    private Context context;

    public HttpRetrieveNotesAsync(AsyncResponse delegate, Context context){
        this.context = context;
        this.delegate = delegate;

    }

    protected String doInBackground(String... params) {
        //context = params[0];
        try {
            HttpRequest req = new HttpRequest(URLConstants.HOST+":"+URLConstants.PORT+"/api/notes");

            response = req.json(HttpRequest.Method.GET);
            Log.d("response",response);

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
        JsonParse jsonParse = new JsonParse();
        ArrayList<Note> noteArrayList;
        if (jsonParse.isSuccess(response)) {
            //JsonUtils.removeSimpleProgressDialog();  //will remove progress dialog
            noteArrayList = jsonParse.getInfo(response);
            delegate.processFinish(noteArrayList);
        } else {
            Toast.makeText(context, jsonParse.getErrorCode(response), Toast.LENGTH_SHORT).show();
            Log.d("jsonParse.getErrorCode(response)", jsonParse.getErrorCode(response));
        }

    }


}
