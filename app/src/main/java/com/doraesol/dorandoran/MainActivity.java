package com.doraesol.dorandoran;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doraesol.dorandoran.map.MapMainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private BackPressCloseHandler backPressCloseHandler;
    @BindView(R.id.vp_main_pager)   ViewPager viewPager;
    @BindView(R.id.tl_main_tabs)    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        int[] tabIcons = {
                R.drawable.ic_list_home,
                R.drawable.ic_list_genogram,
                R.drawable.ic_list_map,
                R.drawable.ic_list_social,
                R.drawable.ic_list_tool4
        };

        super.onCreate(savedInstanceState);
        backPressCloseHandler = new BackPressCloseHandler(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BusProvider.getInstance().register(this);


        Fragment[] arrFragments = new Fragment[5];
        arrFragments[0] = new HomeFragment();
        arrFragments[1] = new FamilyTreeFragment();
        arrFragments[2] = new MapMainFragment();
        arrFragments[3] = new CmtBoardFragment();
        arrFragments[4] = new SettingFragment();

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), arrFragments);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for(int i=0; i<tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }


    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BusProvider.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));
}


    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    class MainPagerAdapter extends FragmentPagerAdapter {

        Fragment[] arrFragments;

        public MainPagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;


        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "TREEMAP";
                case 2:
                    return "MAP";
                case 3:
                    return "SOCIAL";
                case 4:
                    return "SETTING";
                default:
                    return "";
            }
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }
    }
}
