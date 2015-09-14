package com.example.wangzhengze.mypowerword;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wangzhengze.mypowerword.fragment.ReadingFragment;
import com.example.wangzhengze.mypowerword.fragment.TranslateFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setTranslateFragment();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_translate:
                        setTranslateFragment();
                        break;
                    case R.id.menu_read:
                        FragmentManager fm = getFragmentManager();
                        Fragment fragment = ReadingFragment.newInstance("", "");
                        fm.beginTransaction().replace(R.id.main_content, fragment).commit();
                        break;

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void setTranslateFragment() {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = TranslateFragment.newInstance("", "");
        fm.beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
