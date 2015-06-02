package fr.lo53_nkad.android.indoorapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;


public class Calibration extends ActionBarActivity {
    private ActionButton actionBtnY;
    private ActionButton actionBtnN;
    private Context mContext;
    private ImageView mapPin;
    private ImageView map;
    private Position pos ;
    private BgCalibration calibrationTask;
    private  JSONObject samplePoint;
    private static final String TAG = "IndoorPos";
    private String deviceName;
    private String deviceModel;
    private boolean ctrl = true; // cette variable permet de vérifier si l'user a valider ou pas le sample point qu'il vien de créer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        mContext = getApplicationContext();
        map = (ImageView)findViewById(R.id.mapView);
        mapPin = (ImageView)findViewById(R.id.mapPin);
        actionBtnY = (ActionButton)findViewById(R.id.action_buttonY);
        actionBtnN = (ActionButton)findViewById(R.id.action_buttonN);
        pos = new Position(mContext);
        mapPin.setVisibility(View.INVISIBLE);
        actionBtnN.hide();
        actionBtnY.hide();
        deviceName = Build.DEVICE;
        deviceModel = Build.MODEL;


        /*
            Si l'utilisateur touche la map on recupere le pixel de la zone de l'ecran touché
         */
        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(ctrl == true){
                        pos.setMapX((int) event.getX());
                        pos.setMapY((int) event.getY());
                        Log.v(TAG, "(absX:" + pos.getMapX() + " , ordY: " + pos.getMapY() + ")");
                        mapPin.setPadding(pos.getMapX(), pos.getMapY(), 0, 0);
                        mapPin.setVisibility(View.VISIBLE);
                    }
                    actionBtnY.show();
                    actionBtnN.show();
                    ctrl = false;
                }


                // Toast.makeText(mContext, "(absX:" + pos.getMapX() + " , ordY: " + pos.getMapY() + ")", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /*
            Description des actions listener des différent action button
         */
        samplePoint = new JSONObject();

        actionBtnY.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calibrationTask = new BgCalibration();
                try {
                    samplePoint.put("mapX", pos.getMapX());
                    samplePoint.put("mapY", pos.getMapY());
                    samplePoint.put("macAddr", pos.getMacAdd());
                    samplePoint.put("deviceName",deviceName);
                    samplePoint.put("deviceModel",deviceModel);
                    samplePoint.put("mapId",1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, samplePoint.toString());
                calibrationTask.execute(samplePoint);
                actionBtnY.hide();
                actionBtnN.hide();
                ctrl = true;

            }
        });
        actionBtnN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "Canceling", Toast.LENGTH_SHORT).show();
                actionBtnY.hide();
                actionBtnN.hide();
                ctrl = true;
            }
        });
        actionBtnY.hide();
        actionBtnN.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calibration, menu);
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
