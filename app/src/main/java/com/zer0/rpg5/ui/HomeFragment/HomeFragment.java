package com.zer0.rpg5.ui.HomeFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zer0.rpg5.Adapter.SectionPagerAdapter;
import com.zer0.rpg5.R;
import com.zer0.rpg5.ui.Fragments.Timer;
import com.zer0.rpg5.ui.Fragments.Todo;
import com.zer0.rpg5.ui.Fragments.rpgFrag;
import com.zer0.rpg5.ui.gallery.GalleryFragment;
import com.zer0.rpg5.ui.mainMenu.mainMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance()    {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_homefragment, container, false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        return myFragment;
    }

    //Call onActivity Create method


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new Todo(), "To-Do");
        adapter.addFragment(new rpgFrag(), "RPG");
        adapter.addFragment(new Timer(),"Timer");

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }
}