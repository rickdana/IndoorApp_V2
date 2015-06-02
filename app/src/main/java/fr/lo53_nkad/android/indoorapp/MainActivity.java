package fr.lo53_nkad.android.indoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button calibration_btn = (Button)findViewById(R.id.calibration_btn);
        Button localisation_btn = (Button)findViewById(R.id.localisation_btn);
        Button param_btn = (Button)findViewById(R.id.Param_btn);


        /*
            Lancement du mode calibration
         */
        calibration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCalibration(0);
            }
        });
        /*
            Lancement du mode locate me

         */
        localisation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateMe(0);
            }
        });
        /*
            Mode Paramètre
         */
        param_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetting(0);
            }
        });
    }

    /*
        void startCalibration(int var)
            Cette fontion cree un intent pour lancer l'activité "calibration"
     */
    public void startCalibration(int var){
        Intent intentCalibration = new Intent(this,Calibration.class);
        startActivity(intentCalibration);
    }
    /*
        void startSetting(int var)
            Cette fontion cree un intent pour lancer l'activité "calibration"
     */
    public void startSetting(int var){
        Intent intentSetting = new Intent(this,Setting.class);
        startActivity(intentSetting);
    }
    /*
        void startSetting(int var)
            Cette fontion cree un intent pour lancer l'activité "calibration"
     */
    public void locateMe(int var){
        Intent intentlocateMe = new Intent(this,Location.class);
        startActivity(intentlocateMe);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
