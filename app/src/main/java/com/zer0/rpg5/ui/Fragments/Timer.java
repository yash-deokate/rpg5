package com.zer0.rpg5.ui.Fragments;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zer0.rpg5.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Timer extends Fragment {

    View view;
   public static long START_TIME_IN_MILLIS=1500000;
    private TextView timertxt;
    private ImageView playBtn;
    private ImageView stopBtn;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis=START_TIME_IN_MILLIS;
    private ImageView timeSelectBtn;
    private BottomSheetDialog bottomSheetDialogTime;
    private ImageView musicSelectBtn;
    private BottomSheetDialog bottomSheetDialogMusic;
    MediaPlayer player;




    public Timer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_timer, container, false);

        timertxt=view.findViewById(R.id.time);
        playBtn=view.findViewById(R.id.playPause);
        stopBtn=view.findViewById(R.id.stop);
        timeSelectBtn=view.findViewById(R.id.timeSelectBtn);
        musicSelectBtn=view.findViewById(R.id.musicBtn);




        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });


        updateCountDownText();

       timeSelectBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               bottomSheetDialogTime=new BottomSheetDialog(getContext(),R.style.BottomSheetTheme);

               View sheetView=LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout_time,
                       (ViewGroup) getView().findViewById(R.id.bottom_sheet));


               sheetView.findViewById(R.id.btn50).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       START_TIME_IN_MILLIS=3000000;
                       timeLeftInMillis=START_TIME_IN_MILLIS;
                       updateCountDownText();
                       bottomSheetDialogTime.dismiss();
                   }
               });

               sheetView.findViewById(R.id.btn25).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       START_TIME_IN_MILLIS=1500000;
                       timeLeftInMillis=START_TIME_IN_MILLIS;
                       updateCountDownText();
                       bottomSheetDialogTime.dismiss();
                   }
               });
               bottomSheetDialogTime.setContentView(sheetView);
               bottomSheetDialogTime.show();
           }
       });

       musicSelectBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bottomSheetDialogMusic=new BottomSheetDialog(getContext(),R.style.BottomSheetTheme);

               View sheetViewMusic=LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout_music,
                       (ViewGroup) getView().findViewById(R.id.bottom_sheet_music));

               sheetViewMusic.findViewById(R.id.btnLib).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(player!=null){
                           player.release();
                           player=null;
                       }
                       if(player==null){
                           player=MediaPlayer.create(getActivity(), R.raw.lib);
                       }
                       player.start();
                       player.setLooping(true);

                       bottomSheetDialogMusic.dismiss();
                   }
               });

               sheetViewMusic.findViewById(R.id.btnCafe).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(player!=null){
                           player.release();
                           player=null;
                       }
                       if(player==null){
                           player=MediaPlayer.create(getActivity(), R.raw.cafe);
                       }
                       player.start();
                       player.setLooping(true);

                       bottomSheetDialogMusic.dismiss();
                   }
               });

               sheetViewMusic.findViewById(R.id.btnRain).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(player!=null){
                           player.release();
                           player=null;
                       }
                       if(player==null){
                           player=MediaPlayer.create(getActivity(), R.raw.rain);
                       }
                       player.start();
                       player.setLooping(true);

                       bottomSheetDialogMusic.dismiss();
                   }
               });

               sheetViewMusic.findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(player!=null){
                           player.release();
                           player=null;
                       }
                       if(player==null){
                           player=MediaPlayer.create(getActivity(), R.raw.writing);
                       }
                       player.start();
                       player.setLooping(true);

                       bottomSheetDialogMusic.dismiss();
                   }
               });

               sheetViewMusic.findViewById(R.id.btnNone).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(player!=null){
                           player.stop();
                       }
                       else{
                           bottomSheetDialogMusic.dismiss();
                       }
                       bottomSheetDialogMusic.dismiss();
                   }
               });

               bottomSheetDialogMusic.setContentView(sheetViewMusic);
               bottomSheetDialogMusic.show();
           }
       });






        return view;
    }

    private void startTimer(){
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;

                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning=false;
                timeLeftInMillis=START_TIME_IN_MILLIS;
                updateCountDownText();
                playBtn.setImageResource(R.drawable.playicon);
            }
        }.start();

        timerRunning=true;
        playBtn.setImageResource(R.drawable.pauseicon);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning=false;
        playBtn.setImageResource(R.drawable.playicon);
    }

    private void resetTimer(){
        timeLeftInMillis=START_TIME_IN_MILLIS;
        updateCountDownText();

    }

    private void updateCountDownText(){
        int minutes=(int)(timeLeftInMillis/1000)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;

        String timeLeftFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        timertxt.setText(timeLeftFormatted);

    }


//
//    public void onButtonClicked(int num) {
//        switch (num){
//            case 25:
//                START_TIME_IN_MILLIS=1500000;
//                break;
//            case 50:
//                START_TIME_IN_MILLIS=3000000;
//                break;
//                default:
//                    break;
//        }
//    }


//    @Override
//    public void onButtonClicked(String text) {
//        switch (text){
//            case "25":
//                START_TIME_IN_MILLIS=1500000;
//                break;
//            case "50":
//                START_TIME_IN_MILLIS=3000000;
//                break;
//                default:
//                    break;
//        }
//    }



}
