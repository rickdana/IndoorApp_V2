package fr.lo53_nkad.android.indoorapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 31/05/15.
 */
public class BgLocation extends AsyncTask<String,String,JSONObject> {
    private static final String TAG = "HttpConnection";
    private DataOutputStream outputStream;
    private InputStream inputStream;
    JSONObject location;
    @Override
    protected JSONObject doInBackground(String... pararms) {
        String serverUrl[];
        serverUrl = pararms.clone();
        HttpURLConnection httpURLConnection = null;

        try{
            URL url = new URL(serverUrl[0]);
            Log.v(TAG,url.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/text");
            httpURLConnection.connect();
            // Envoie de la requete
            int responseCode = httpURLConnection.getResponseCode();
            Log.v(TAG,"Envoi d'une requete GET a l'@ : "+url.toString());
            Log.v(TAG,"Code de reponde ! "+responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();
            switch (responseCode){
                case HttpURLConnection.HTTP_OK:
                    while((inputLine = in.readLine())!= null){
                        response.append(inputLine);
                    }
                    in.close();
                    Log.v(TAG, response.toString());
                    try{
                        location = new JSONObject(response.toString());
                    }catch (JSONException jo){
                        jo.printStackTrace();
                    }
                    Log.v("REQ result :",location.toString());
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    try{
                        location = new JSONObject("Error: Server Not found 404");
                    }catch (JSONException jo){
                        jo.printStackTrace();
                    }
                    Log.v("REQ result :",location.toString());
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    try{
                        location = new JSONObject("Error:Server error 500");
                    }catch (JSONException jo){
                        jo.printStackTrace();
                    }
                    Log.v("REQ result :",location.toString());
                    break;
            }

          /* inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();
            while (data != -1) {
                char current = (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }*/



        }catch (MalformedURLException m){
            m.printStackTrace();
            Log.v(TAG, "MalformedUrl");
        }catch (IOException io){
            io.printStackTrace();
            Log.v(TAG, "IoExeption");
        }finally {
            httpURLConnection.disconnect();
        }
        Log.v("REQ result :",location.toString());
        return location;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
