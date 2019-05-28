package com.example.pocketclass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class TeacherActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment

    public static String uname;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        // listView = (ListView) findViewById(R.id.mainList);
        BottomNavigationView navigation = findViewById(R.id.navigation1);
        Bundle bundle = getIntent().getExtras();

        uname=bundle.getString("uname");
        context=getBaseContext();
        initFragment();
    }
    private void initFragment() {
        fragment1 = new StudentManager();
        fragment2 = new TeacherInformation();
        fragments = new Fragment[]{fragment1, fragment2};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainView, fragment1).show(fragment1).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation1);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                }
                case R.id.navigation_notifications: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }

                    return true;
                }

            }
            return false;
        }
    };

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainView, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
