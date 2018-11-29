package com.example.edmol.webview;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class control extends AppCompatActivity {

    CoordinatorLayout display2;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private TextView mCargaTexto;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        display2 = (CoordinatorLayout) findViewById(R.id.Fondo);
        mProgressBar = (ProgressBar) findViewById(R.id.pbProgreso);
        mCargaTexto = (TextView) findViewById(R.id.tvCargaCompleta);

        String fondoActual;
        fondoActual = getIntent().getExtras().getString("fondoActual");
        switch (fondoActual) {
            case "colorFondo1":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
                break;
            case "colorFondo2":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
                break;
            case "colorFondo3":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3));
                break;
            case "colorFondo4":
                display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4));
                break;
            default:
                Toast.makeText(this, "Algo malo pasó", Toast.LENGTH_SHORT).show();
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCargaTexto.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: tab1Manual tab1 = new tab1Manual();
                return tab1;

                case 1: tab2Automatico tab2 = new tab2Automatico();
                return tab2;

                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position){
            switch (position){
                case 0: return "Manual";
                case 1: return "Automático";
            }
            return null;
        }
    }
}