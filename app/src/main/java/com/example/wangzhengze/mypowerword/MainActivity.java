package com.example.wangzhengze.mypowerword;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wangzhengze.mypowerword.fragment.DailyFragment;
import com.example.wangzhengze.mypowerword.fragment.ReadingFragment;
import com.example.wangzhengze.mypowerword.fragment.TranslateFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setCurrentFragment(R.id.menu_translate);

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                setCurrentFragment(menuItem.getItemId());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void setCurrentFragment(int id) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = null;

        switch (id) {
            case R.id.menu_translate:
                fragment = TranslateFragment.newInstance("", "");
                break;
            case R.id.menu_read:
                fragment = ReadingFragment.newInstance("", "");
                break;
            case R.id.menu_daily:
                fragment = DailyFragment.newInstance("", "");
        }
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
