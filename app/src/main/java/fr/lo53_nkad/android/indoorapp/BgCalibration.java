package fr.lo53_nkad.android.indoorapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 29/05/15.
 */
public class BgCalibration extends AsyncTask<JSONObject,String,String> {
    private static final String TAG = "HttpConnection";
    private  static final String TASKEND = "Requete Terminée";
    String uri =  "http://10.42.0.1:8080/server/calibration";
    DataOutputStream outputStream;
    DataInputStream inputStream;

    @Override
    protected String doInBackground(JSONObject...samplePoint){
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            // Ici on envoie la requette post
            outputStream = new DataOutputStream(httpURLConnection.getOutputStream ());
            outputStream.writeBytes(samplePoint[0].toString());
            Log.v(TAG, samplePoint[0].toString());
            outputStream.flush();
            outputStream.close();
            // On récupère le code de retour de la requette
            int httpResult = httpURLConnection.getResponseCode();
            if(httpResult == httpURLConnection.HTTP_OK){

                Log.v(TAG, "Connexion au serveur réussi");
                Log.v(TAG,httpURLConnection.getURL().toString());
            }else {
                Log.v(TAG,"Connexion au serveur impossible");
            }
        }catch (MalformedURLException me){
            me.printStackTrace();
            Log.v(TAG, "MalformedUrl");

        }catch (IOException io){
            io.printStackTrace();
            Log.v(TAG, "IoExeption");
        }finally {
            httpURLConnection.disconnect();
        }
        return TASKEND;
    }

    protected void onProgressUpdate(String... progress) {
        Log.v(TAG,"Requete en cours");
    }

    protected void onPostExecute(String result) {
        Log.v(TAG,"Requete terminée");
        this.cancel(true);

    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
