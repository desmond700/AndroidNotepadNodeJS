package com.example.androidnotepadnodejs.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androidnotepadnodejs.ItemListActivity;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IsHostReachable extends AsyncTask<Void, Void, Boolean>  {

    public interface AsyncResponse{
        void processFinish(Boolean isConnected);
    }


    public AsyncResponse delegate;

    public IsHostReachable(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    protected Boolean doInBackground(Void ...params) {
        Log.d("isHostAvailable", "run");
        Boolean isConnected = isHostAvailable("10.0.2.2", 3000,1000);

        return isConnected;
    }

    protected void onPostExecute(Boolean result) {
        //do something with response
        Log.d("isHostAvailable", Boolean.toString(result));
        delegate.processFinish(result);
    }

    public static boolean isHostAvailable(final String host, final int port, final int timeout) {
        try (final Socket socket = new Socket()) {
            final InetAddress inetAddress = InetAddress.getByName(host);
            final InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, port);

            socket.connect(inetSocketAddress, timeout);
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}