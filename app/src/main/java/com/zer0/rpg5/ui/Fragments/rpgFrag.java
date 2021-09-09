package com.zer0.rpg5.ui.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zer0.rpg5.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class rpgFrag extends Fragment {

    View view;

    ProgressBar xpBar;

    ProgressBar lvlBar;
    TextView lvlText;
    TextView xpText;
    TextView nametxt;
    TextView httxt;
    TextView powertxt;

    TextView quotxt;
    ImageView imageView;

    public rpgFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_rpg, container, false);
        xpText=view.findViewById(R.id.xp);
        xpBar=view.findViewById(R.id.xpBar);
        lvlText=view.findViewById(R.id.lvl);
        nametxt=view.findViewById(R.id.Nametxt);
        httxt=view.findViewById(R.id.Httxt);
        powertxt=view.findViewById(R.id.Powertxt);
        quotxt=view.findViewById(R.id.Qotetxt);
        lvlBar=view.findViewById(R.id.lvlBar);
        imageView=view.findViewById(R.id.imgView);


        String s=String.valueOf(getDefaults("xp",getContext()));
        xpText.setText(s);


        String st=String.valueOf(getDefaults("lvl",getContext()));
        lvlText.setText(st);


        int current_progress=xpBar.getProgress();
        if(current_progress>xpBar.getMax()){
            xpBar.setProgress(0);
            xpBar.setMax(2*current_progress);
            setDefaults("xp",0,getContext());
            int temp=getDefaults("lvl",getContext());
            temp=temp+1;
            setDefaults("lvl",temp,getContext());
        }
        else {
            xpBar.setProgress(getDefaults("xp",getContext()));
        }
        lvlBar.setProgress(getDefaults("lvl",getContext()));


        int current_progress_lvl=lvlBar.getProgress();
        if(current_progress_lvl>=lvlBar.getMax()){
            lvlText.setText("0");
            lvlBar.setProgress(0);
            xpBar.setMax(current_progress_lvl+1);
            setDefaults("lvl",0,getContext());
//                imageView.setBackgroundResource(R.drawable.ninja_frog);
            int character_cnt_temp=getDefaults("charc",getContext());
            character_cnt_temp=character_cnt_temp+1;
            setDefaults("charc",character_cnt_temp,getContext());

        }
        else {
            lvlBar.setProgress(getDefaults("lvl",getContext()));
        }


        int character_cnt=getDefaults("charc",getContext());
        switch (character_cnt){
            case 0:
                nametxt.setText("Ninja Frog");
                httxt.setText("5.3 px");
                powertxt.setText("Ninja techniques");
                quotxt.setText(Html.fromHtml("&ldquo; " + "Eat that frog Before he eats you" + " &rdquo;"));
                break;
            case 1:
                nametxt.setText("Virtual Guy");
                httxt.setText("6.2 px");
                powertxt.setText("Technology");
                quotxt.setText(Html.fromHtml("&ldquo; " + "Technology was a Mistake.... Not" + " &rdquo;"));
                break;
            case 2:
                nametxt.setText("Pink Man");
                httxt.setText("6.5 px");
                powertxt.setText("Filth");
                quotxt.setText(Html.fromHtml("&ldquo; " + "Aye boss.. Can I habe pitzza please?" + " &rdquo;"));
                break;
            case 3:
                nametxt.setText("Mask Man");
                httxt.setText("5.7 px");
                powertxt.setText("Power of Mischief");
                quotxt.setText(Html.fromHtml("&ldquo; " + "I wear mask so that people dont need to wear one" + " &rdquo;"));
                break;
            default:
                break;
        }


        xpBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        lvlBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        xpBar.setScaleY(1.5f);
        lvlBar.setScaleY(1.5f);

        return view;
    }


    ///////////SHARED PREFERENCES FOR XP /////////////////////////////
    public static void setDefaults(String key, int value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key,0);
    }

    //////////REFRESH AFTER TAB CHANGE AND DATA CHANGE//////////////
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Write down your refresh code here, it will call every time user come to this fragment.
            //If you are using listview with custom adapter, just call notifyDataSetChanged().
            String s=String.valueOf(getDefaults("xp",getContext()));
            xpText.setText(s);
            String st=String.valueOf(getDefaults("lvl",getContext()));
            lvlText.setText(st);
            int current_progress=xpBar.getProgress();
            if(current_progress>=xpBar.getMax()){
                xpText.setText("0");
                xpBar.setProgress(0);
                xpBar.setMax(2*current_progress);
                setDefaults("xp",0,getContext());
                int temp=getDefaults("lvl",getContext());
                temp=temp+1;
                setDefaults("lvl",temp,getContext());

                setUserVisibleHint(true);

            }
            else {
                xpBar.setProgress(getDefaults("xp",getContext()));
            }



            int current_progress_lvl=lvlBar.getProgress();
            if(current_progress_lvl>=lvlBar.getMax()){
                lvlText.setText("0");
                lvlBar.setProgress(0);
                xpBar.setMax(current_progress_lvl+1);
                setDefaults("lvl",0,getContext());
//                imageView.setBackgroundResource(R.drawable.ninja_frog);
                int character_cnt_temp=getDefaults("charc",getContext());
                character_cnt_temp=character_cnt_temp+1;
                setDefaults("charc",character_cnt_temp,getContext());
                setUserVisibleHint(true);

            }
            else {
                lvlBar.setProgress(getDefaults("lvl",getContext()));
            }


            int character_cnt=getDefaults("charc",getContext());
            switch (character_cnt){
                case 0:
                    imageView.setImageResource(R.drawable.ninja_frog);
                    nametxt.setText("Ninja Frog");
                    httxt.setText("5.3 px");
                    powertxt.setText("Ninja techniques");
                    quotxt.setText(Html.fromHtml("&ldquo; " + "Eat that frog Before he eats you" + " &rdquo;"));
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.virtual_guy);
                    nametxt.setText("Virtual Guy");
                    httxt.setText("6.2 px");
                    powertxt.setText("Technology");
                    quotxt.setText(Html.fromHtml("&ldquo; " + "Technology was a Mistake.... Not" + " &rdquo;"));
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.pink_man);
                    nametxt.setText("Pink Man");
                    httxt.setText("6.5 px");
                    powertxt.setText("Filth");
                    quotxt.setText(Html.fromHtml("&ldquo; " + "Aye boss.. Can I habe pitzza please?" + " &rdquo;"));
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.mask_man);
                    nametxt.setText("Mask Man");
                    httxt.setText("5.7 px");
                    powertxt.setText("Power of Mischief");
                    quotxt.setText(Html.fromHtml("&ldquo; " + "I wear mask so that people dont need to wear one" + " &rdquo;"));
                    default:
                        break;
            }


            xpBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            lvlBar.getProgressDrawable().setColorFilter(
                    getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            xpBar.setScaleY(1.5f);
            lvlBar.setScaleY(1.5f);

        }



    }





}
