package com.example.sai.soundequalizer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.media.MediaRecorder.OutputFormat;

import java.lang.Object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.pm.PackageManager.*;
import static android.view.WindowManager.*;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
  public boolean rec=false;
    public boolean play=false;
   public short b0=1500;
    public short b1=1500;
    public short b2=1500;
    public short b3=1500;
    public short b4=1500;
   short[] array = new short[]{0,0,0,0,0};
    final Context context = this;
    private static final String TAG = "RAWAUDIO";
    //modified today
    public TextView txt;
    private boolean isRecording = false;
    private boolean isplaying = false;
    public ImageView mImageViewRec;
    public ImageView mImageViewplay;
    public AudioTrack audioTrack2;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt = (TextView) findViewById(R.id.text);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
         array = bundle.getShortArray("MyArray");
      /*   b0=array[0];
            b1=array[1];
            b2=array[2];
            b3=array[3];
            b4=array[4]; */
          //  txt.setText("SUCCESS "+Short.toString(array[0])+" "+Short.toString(array[1])+" "+Short.toString(array[2])+" "+Short.toString(array[3])+"  "+Short.toString(array[4]));
          //  Toast.makeText(MainActivity.this, "INTENT RECEIVED :"+Short.toString(array[1]) ,Toast.LENGTH_SHORT).show();

        };

       mImageViewRec = (ImageView)findViewById(R.id.btnRecord);
        mImageViewplay = (ImageView)findViewById(R.id.btnPlay);
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
       // getStreamVolume();
        int previousVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);
      //  Toast.makeText(MainActivity.this, "Previous volume="+Integer.toString(previousVolume), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Previous volume="+Integer.toString(previousVolume));
        //modified today
        // txvResult=(TextView)findViewById(R.id.txvResult);
    /*    Button record = (Button) findViewById(R.id.buttonRecord);
        Button play = (Button) findViewById(R.id.buttonPlayback);
        Button stop = (Button) findViewById(R.id.buttonStop);
      //  txt = (TextView) findViewById(R.id.text);

        record.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                Log.d(TAG, "ENTERED ASYNC RECORDING");

                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Log.d(TAG, "ENTERED DOING IN BACKGROUND");

                        isRecording = true;
                        record();
                        //modified today
                        //getSpeechInput();
                        return null;
                    }

                };
                task.execute();
            }
        });

        play.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                playback();
            }
        });

        stop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isRecording = false;
            }
        });

        */
        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "STORAGE and MICROPHONE  permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            showDialogOK("STORAGE and MICROPHONE Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)

                .create()
                .show();
    }
    private  boolean checkAndRequestPermissions() {
        int permissionwriteExternalStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordaudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (recordaudioPermission != PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);

        }
        if (permissionwriteExternalStorage != PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    private void record() {

        int frequency = 11025;
        int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

        File file =
                new File(Environment.getExternalStorageDirectory(), "raw.pcm");

        // Create the new file.
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, "IO Exception", e);
        }

        try {
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            int bufferSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration,
                    audioEncoding);
            short[] buffer = new short[bufferSize];

            // Create a new AudioRecord object to record the audio.
            AudioRecord audioRecord =
                    new AudioRecord(MediaRecorder.AudioSource.MIC,
                            frequency,
                            channelConfiguration,
                            audioEncoding, bufferSize);
            // audioRecord.setOutputFormat(OutputFormat.MPEG_4);
            //MODIFIED FOR PLAYBACK DURING RECORDING
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    frequency,
                    AudioFormat.CHANNEL_OUT_MONO,
                    audioEncoding,
                    bufferSize,
                    AudioTrack.MODE_STREAM);
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
     /*      int sessionID=audioTrack.getAudioSessionId();
          Equalizer equalizer=new Equalizer(0,sessionID);
          */       /*     setVolumeControlStream(AudioManager.STREAM_MUSIC);
            int sessionID=audioTrack.getAudioSessionId();
            short boostStrength=1000;
            BassBoost bassBoost=new BassBoost(0,sessionID);
            bassBoost.setStrength(boostStrength);
           int suc= bassBoost.setEnabled(true);
            txt.setText("SUCCESS "+Integer.toString(suc));
            */

         /*   equalizer.setBandLevel((short)0,b0);
            equalizer.setBandLevel((short)1,b1);
            equalizer.setBandLevel((short)2,b2);
            equalizer.setBandLevel((short)3,b3);
            equalizer.setBandLevel((short)4,b4);*/

    /*   equalizer.setBandLevel((short)0,(short)1500);
            equalizer.setBandLevel((short)1,(short)1500);
            equalizer.setBandLevel((short)2,(short)1500);
            equalizer.setBandLevel((short)3,(short)1500);
            equalizer.setBandLevel((short)4,(short)1500);
            */
     /*   equalizer.setBandLevel((short)0,(short)array[0]);
                equalizer.setBandLevel((short)1,(short)array[1]);
                equalizer.setBandLevel((short)2,(short)array[2]);
                equalizer.setBandLevel((short)3,(short)array[3]);
                equalizer.setBandLevel((short)4,(short)array[4]);
            Toast.makeText(MainActivity.this, "intentvalue :" + Short.toString(array[4]),
                    Toast.LENGTH_SHORT).show();
*/
     /*     int suc1= equalizer.setEnabled(true);
           audioTrack.attachAuxEffect(equalizer.getId());

            audioTrack.setAuxEffectSendLevel(1.0f);   */
      /*      AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

            int previousVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);
            Toast.makeText(MainActivity.this, "Previous volume="+Integer.toString(previousVolume), Toast.LENGTH_SHORT).show();
           */
            audioRecord.startRecording();


            while (isRecording) {
                //MODIFIED FOR PLAYBACK DURING RECORDING
                audioTrack.play();
                //getSpeechInput();
                //MODIFIED FOR PLAYBACK DURING RECORDING
                audioTrack.write(buffer, 0, bufferSize);
                int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                for (int i = 0; i < bufferReadResult; i++) {
                    dos.writeShort(buffer[i]);
                }
                //modifications to speech to text


            }


            audioRecord.stop();
            audioTrack.stop();
            audioRecord.release();
            audioTrack.release();
            dos.close();
        } catch (Throwable t) {
            Log.d(TAG, "An error occurred during recording", t);
        }
    }

    private void playback() {

            int frequency = 11025;
            int channelConfiguration = AudioFormat.CHANNEL_OUT_MONO;
            int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
            Log.d(TAG, "2.ENTERED playback");

            File file =
                    new File(Environment.getExternalStorageDirectory(), "raw.pcm");

            // Short array to store audio track (16 bit so 2 bytes per short)
            int audioLength = (int) (file.length() / 2);
            short[] audio = new short[audioLength];

            try {
                InputStream is = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                int i = 0;
                while (dis.available() > 0) {
                    audio[i] = dis.readShort();
                    i++;
                }

                // Close the input streams.
                dis.close();

                // Create and play a new AudioTrack object
                audioTrack2 = new AudioTrack(AudioManager.STREAM_MUSIC,
                        frequency,
                        channelConfiguration,
                        audioEncoding,
                        audioLength,
                        AudioTrack.MODE_STREAM);
                // Equalizer equalizer = new Equalizer(0,audioTrack.getAudioSessionId());
                //equalizer.setEnabled(true);
                // while(isplaying){
                setVolumeControlStream(AudioManager.STREAM_MUSIC);
                audioTrack2.setVolume (1.0f);
             /*     int sessionID=audioTrack2.getAudioSessionId();
                Equalizer equalizer=new Equalizer(0,sessionID);

               equalizer.setBandLevel((short)0,(short)1500);
                equalizer.setBandLevel((short)1,(short)1500);
                equalizer.setBandLevel((short)2,(short)1500);
                equalizer.setBandLevel((short)3,(short)1500);
                equalizer.setBandLevel((short)4,(short)1500);

              equalizer.setBandLevel((short)0,(short)array[0]);
                equalizer.setBandLevel((short)1,(short)array[1]);
                equalizer.setBandLevel((short)2,(short)array[2]);
                equalizer.setBandLevel((short)3,(short)array[3]);
                equalizer.setBandLevel((short)4,(short)array[4]);


                int suc1= equalizer.setEnabled(true);
                audioTrack2.attachAuxEffect(equalizer.getId());
                audioTrack2.setAuxEffectSendLevel(1.0f);
                */
             if(audioLength>0) {
                 Log.d(TAG, "3.ENTERED playloop");
                 audioTrack2.play();
                 audioTrack2.write(audio, 0, audioLength);
                 Log.d(TAG, "4.END playloop");
             }
                // }
               /* if (!isplaying) {
                    audioTrack2.pause();
                    audioTrack2.stop();
                }
                */

            } catch (Throwable t) {
                Log.d(TAG, "An error occurred during playback", t);
            }


       // Log.d(TAG, "STOP play");

    }


    public void onSendMessage(View view)
    {
        if((!rec)&&(!play)) {
            Intent intent = new Intent(this, speechtotext.class);
            startActivity(intent);
        }
    }
    public void newrecord(View view)
    {
        if(!play) {
            rec = !rec;
            if (rec) {
                mImageViewRec.setImageResource(R.drawable.btn_pause);
                Log.d(TAG, "ENTERED ASYNC RECORDING");

                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Log.d(TAG, "ENTERED RECORD DOING IN BACKGROUND");

                        isRecording = true;
                        record();
                        //modified today
                        //getSpeechInput();
                        return null;
                    }

                };
                task.execute();
            } else {
                mImageViewRec.setImageResource(R.drawable.btn_record);
                isRecording = false;
              //  audioTrack2.pause();
               // audioTrack2.stop();
               // Toast.makeText(MainActivity.this, "RECORD button pressed", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void newplay(View view)
    {
        if(!rec) {
            play = !play;
            if (play) {
                mImageViewplay.setImageResource(R.drawable.btn_pause);
               //playback();
              @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Log.d(TAG, "1.ENTERED playing IN BACKGROUND");

                      //  isplaying = true;
                        playback();
                        //modified today
                        //getSpeechInput();
                        Log.d(TAG, "5.END OF playing IN BACKGROUND");
                        return null;
                    }
                  protected void onPostExecute(Void result) {
                      Log.d(TAG, "6.ENTERED playing onPostExecute");
                    //  mImageViewplay.setImageResource(R.drawable.btn_play);
                  }

                };
                task.execute();

                Log.d(TAG, "ASYNC TASK IN PLAY FINISHED");
                Toast.makeText(MainActivity.this, "PLAY button pressed", Toast.LENGTH_SHORT).show();
            } else {
                mImageViewplay.setImageResource(R.drawable.btn_play);
               // isplaying = false;
                 audioTrack2.pause();
                 audioTrack2.flush();
                 audioTrack2.release();
               // Toast.makeText(MainActivity.this, "PLAY button stopped", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void onEqualizer(View view)
    {  if((!rec)&&(!play)) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    }
    public void onInfo(View view)
    {
       /** if((!rec)&&(!play)) {
           new AlertDialog.Builder(this)

                    .setTitle("SOUNDTRAIL")
                    .setMessage("THIS APP HELPS IN LISTENING TO SURROUNDINGS FROM HEADPHONES.THIS HELPS PEOPLES WITH HEARING DIFFICULTIES")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show();
        }*/
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}
