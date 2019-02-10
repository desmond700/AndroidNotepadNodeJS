package com.example.androidnotepadnodejs.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonParse {

    private final String KEY_SUCCESS = "status";
    private final String KEY_MSG = "message";

    public JsonParse() {

    }

    public boolean isSuccess(String response) {
        if (response != null) {
            return true;
        } else {

            return false;
        }

    }

    public String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(KEY_MSG);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    public ArrayList<Note> getInfo(String response) {
        Log.d("getInfo json: ", response);
        ArrayList<Note> noteList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (!jsonArray.isNull(0)) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    Note noteModel = new Note();
                    JSONObject jsonobj = jsonArray.getJSONObject(i);
                    noteModel.set_ID(jsonobj.getString("_id"));
                    noteModel.setTitle(jsonobj.getString("title"));
                    noteModel.setNote(jsonobj.getString("note"));
                    noteModel.setDate(jsonobj.getString("date"));
                    noteList.add(noteModel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return noteList;
    }

    public Note getNote(String response) {
        Log.d("getInfo json: ", response);
        Note noteModel = new Note();
        try {
            JSONObject jsonobj = new JSONObject(response);
            if (jsonobj.length() > 0) {
                noteModel.set_ID(jsonobj.getString("_id"));
                noteModel.setTitle(jsonobj.getString("title"));
                noteModel.setNote(jsonobj.getString("note"));
                noteModel.setDate(jsonobj.getString("date"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("json error", e.getMessage());
        }
        return noteModel;
    }
}
