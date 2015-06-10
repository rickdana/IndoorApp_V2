package fr.lo53_nkad.android.indoorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Setting extends ActionBarActivity {
    private JSONObject params;
    private EditText ipAddr;
    private EditText portNum;
    private Button valideBtn;
    private  Button cancelBtn;
    private Context context;
    String fileName = "Setting.txt";
    FileOutputStream outputStream;
    FileInputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //File fParams = new File(context.getFilesDir()+"/",fileName);
        /*
         *  On associe les diférent element au fichier XML de l'activité
         */
        ipAddr = (EditText)findViewById(R.id.ip_edit);
        portNum = (EditText)findViewById(R.id.port_edit);
        valideBtn = (Button)findViewById(R.id.conf_btn_yes);
        cancelBtn = (Button)findViewById(R.id.conf_btn_no);
        context = getApplicationContext();
        params = new JSONObject();

        /*
            Création des différent listener
         */
        // Bouton Validé listener
        valideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ipAddr.getText().equals("0.0.0.0") && portNum.getText().equals(8080)){
                   //Valeurs par défaut donc pas de changement on ne fait rien
               }else {
               // SI il y a modification d'un paramètre
                 /*
                 * On construint un objet JSON "params" de paramettre cette objet sera stocké dans un fichier en
                  * mémoire et sera partagé entre les différentes activités. Seul l'activité Setting peut l'éditer.
                 */
                   try{
                       params.put("ip",ipAddr.getText());
                       params.put("port",portNum.getText());
                   }catch (JSONException e){
                       e.printStackTrace();
                   }
                     /*
                     * On ecrit les nouveau prametres dans un fichier
                      */
                   try{
                       outputStream = openFileOutput(fileName,context.MODE_PRIVATE);
                       outputStream.write(params.toString().getBytes());
                       outputStream.close();
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                   /*
                   Lecture du fichier
                    */
                   final StringBuffer storedString = new StringBuffer();
                   try {
                       inputStream = openFileInput(fileName);
                       DataInputStream dataIO = new DataInputStream(inputStream);
                       String strLine = null;
                       while((strLine = dataIO.readLine()) != null) {
                           Log.v("FileTest", strLine);
                           storedString.append(strLine);
                       }
                       dataIO.close();
                       inputStream.close();

                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
            }
        });
        // Bouton Cancel listener
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
