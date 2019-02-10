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
            HttpRequest req = new HttpRequest("http://10.0.2.2:3000/api/notes");

            response = req.json(HttpRequest.Method.GET);
            Log.d("response",response);
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
            ArrayList<Note> noteArrayList;
            if (jsonParse.isSuccess(response)) {
                //JsonUtils.removeSimpleProgressDialog();  //will remove progress dialog
                noteArrayList = jsonParse.getInfo(response);
                Log.d("jsonParseresponse", response);
                delegate.processFinish(noteArrayList);
            } else {
                Toast.makeText(context, jsonParse.getErrorCode(response), Toast.LENGTH_SHORT).show();
                Log.d("jsonParse.getErrorCode(response)", jsonParse.getErrorCode(response));
            }
        }else{
            Log.d("no network connect", "CONNECTFAILED");
            noConnectionDialog();
        }
    }

    public void noConnectionDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Info");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setMessage("Server connection failed, Cross check your internet connectivity, or make sure your server is running and try again");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
