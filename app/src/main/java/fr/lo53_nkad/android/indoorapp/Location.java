package fr.lo53_nkad.android.indoorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.software.shell.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;


public class Location extends ActionBarActivity {
    Position pos; //représente un couple de coordonnées et sera affiché sur la map
    private Context mContext;
    private TouchImageView map; // represente la map
    private ImageView mapPin; // Pin qui pour identifier la position
    private ActionButton locationBtn; // Bouton pour lancer une reqette de locatlisation
    private JSONObject location; //Objet json pour recupérer le reponse du serveur
    private FileInputStream inputStream; // Cet objet permet de la lecture dans le fichier de paramètres et de recupérer ainsi l'ip et le port du serveur
    private String fileName = "Setting.txt"; // nom du fichier de paramètres
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
          /*
                   Lecture du fichier
                    */
        final StringBuffer storedString = new StringBuffer();
        try {
            inputStream = openFileInput(fileName);
            DataInputStream dataIO = new DataInputStream(inputStream);
            String strLine = null;
            while((strLine = dataIO.readLine()) != null) {
                //Log.v("FileTest", strLine);
                storedString.append(strLine);
                uri = strLine;
            }
            dataIO.close();
            inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        Log.v("FileTest", uri);
        mContext = getApplicationContext();
        pos = new Position(getApplicationContext());
        /*
            On associe chaque elément au fichier de description XML
         */
        pos = new Position(mContext);
        map = (TouchImageView)findViewById(R.id.mapView);
        mapPin = (ImageView)findViewById(R.id.mapPin);
        locationBtn = (ActionButton)findViewById(R.id.locate_me);
        //On cache le mapPin
        mapPin.setVisibility(View.INVISIBLE);
        location = new JSONObject();

        /*
            Définition des différent listener
         */
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = "http://192.168.1.73:1337/Android/getPostion?mac="+pos.getMacAdd();//"http://192.168.2.9:8080/server/location";
                Log.v("Location REQ GET :",pos.getMacAdd());
                Log.v("Location REQ GET :",uri);
                BgLocation locationTask = new BgLocation();
                try{
                    location =  locationTask.execute(uri,pos.getMacAdd()).get();
                    Log.v("Location REQ GET :",location.toString());
                }catch (InterruptedException it){
                    it.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.v("Location REQ GET :",location.toString());
                try {
                    pos.setMapX(location.getInt("mapX"));
                    pos.setMapY(location.getInt("mapX"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mapPin.setPadding(pos.getMapX(), pos.getMapY(), 0, 0);
                mapPin.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
