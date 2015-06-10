package fr.lo53_nkad.android.indoorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

}
