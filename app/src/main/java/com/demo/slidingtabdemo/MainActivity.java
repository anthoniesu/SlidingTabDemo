package com.demo.slidingtabdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.slidingtabdemo.fragment.TabFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initTabFragment(savedInstanceState);
    }

    private void initActionBar() {
        getSupportActionBar().setTitle("");
    }

    private void initTabFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            TabFragment tabFragment = new TabFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_fragment, tabFragment)
                    .commit();
        }
    }
}
