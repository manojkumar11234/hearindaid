package com.example.sai.soundequalizer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    //ArrayList<Short> array = new ArrayList<Short>();
    short[] array = new short[5];
    private static final String TAG2 = "EQUALIZER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  /*      View view1 = findViewById(R.id.band1);
        TextView title1 = (TextView) view1.findViewById(R.id.text);
        title1.setText("60HZ");
        SeekBar Seekbar1 =(SeekBar)view1.findViewById(R.id.simpleSeekBar);
        array[0]=(short)((Seekbar1.getProgress()*100)-1500);
        View view2 = findViewById(R.id.band2);
        TextView title2 = (TextView) view2.findViewById(R.id.text);
        title2.setText("230HZ");
        SeekBar Seekbar2 =(SeekBar)view2.findViewById(R.id.simpleSeekBar);
        array[1]=(short)((Seekbar2.getProgress()*100)-1500);
        View view3 = findViewById(R.id.band3);
        TextView title3 = (TextView) view3.findViewById(R.id.text);
        title3.setText("910HZ");
        SeekBar Seekbar3 =(SeekBar)view3.findViewById(R.id.simpleSeekBar);
        array[2]=(short)((Seekbar3.getProgress()*100)-1500);
        View view4 = findViewById(R.id.band4);
        TextView title4 = (TextView) view4.findViewById(R.id.text);
        title4.setText("3600HZ");
        SeekBar Seekbar4 =(SeekBar)view4.findViewById(R.id.simpleSeekBar);
        array[3]=(short)((Seekbar4.getProgress()*100)-1500);
        View view5 = findViewById(R.id.band5);
        TextView title5 = (TextView) view5.findViewById(R.id.text);
        title5.setText("14000HZ");
        SeekBar Seekbar5 =(SeekBar)view5.findViewById(R.id.simpleSeekBar);
        array[4]=(short)((Seekbar5.getProgress()*100)-1500);
        // perform seek bar change listener event used for getting the progress value
        Seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          short progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (short)((progress*100)-1500);
                array[0]=progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Main2Activity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (short)((progress*100)-1500);
                array[1]=progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Main2Activity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (short)((progress*100)-1500);
                array[2]=progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Main2Activity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Seekbar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (short)((progress*100)-1500);
                array[3]=progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Main2Activity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Seekbar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (short)((progress*100)-1500);
                array[4]=progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Main2Activity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Button button = (Button) findViewById(R.id.buttonsettings);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(Main2Activity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.putExtra("MyArray", array);
               // Toast.makeText(Main2Activity.this, "intentvalue :" + Short.toString(array[1]),
               //         Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
     */
      /*  public void setEqualizer(View view)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        */
       Intent intent = new Intent();
        intent.setAction("android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL");
        if((intent.resolveActivity(getPackageManager()) != null)) {
            startActivityForResult(intent,10);
            // REQUEST_EQ is an int of your choosing
            Log.d(TAG2, "EQUALIZER FOUND");
        } else {
            // No equalizer found :(
            Log.d(TAG2, "NO EQUALIZER");
            Toast.makeText(Main2Activity.this, "NO DEFAULT EQUALIZER FOUND IN YOUR PHONE",
                           Toast.LENGTH_SHORT).show();
        }

    }
}
