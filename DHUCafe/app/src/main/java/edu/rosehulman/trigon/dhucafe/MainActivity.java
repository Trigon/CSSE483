package edu.rosehulman.trigon.dhucafe;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import edu.rosehulman.trigon.dhucafe.items.CafeContent;
import edu.rosehulman.trigon.dhucafe.items.NewsContent;

public class MainActivity extends AppCompatActivity implements  NewsLIstFragment.OnListFragmentInteractionListener,CafeListFragment.OnListFragmentInteractionListener,LoginFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static boolean mBackKeyPressed = false;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setOffscreenPageLimit(1);
        //Paint the StatusBar to DarkPrimary prevent from becoming transparent.
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //FAB暂时保留
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, mViewPager.getCurrentItem()+"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                try {
//                    //利用Intent打开微信
//                    Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    //若无法正常跳转，在此进行错误处理
//                    Snackbar.make(view, "fuck", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(NewsContent.NewsItem item) {
        Log.d("callback",item.id);
        mSectionsPagerAdapter.passNewsItem(item);
        mSectionsPagerAdapter.setDetails(0,true);
        mSectionsPagerAdapter.notifyDataSetChanged();
        Log.d("have notified",item.id+"");

    }

    @Override
    public void onListFragmentInteraction(CafeContent.CafeItem item) {
        Log.d("callback",item.id);
        mSectionsPagerAdapter.passCafeItem(item);
        mSectionsPagerAdapter.setDetails(1,true);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        int position = mViewPager.getCurrentItem();
        if (mSectionsPagerAdapter.isDetail(position)){
            mSectionsPagerAdapter.setDetails(position,false);
            mSectionsPagerAdapter.notifyDataSetChanged();
            return;
        }
        if(!mBackKeyPressed){
            Toast.makeText(this, getResources().getString(R.string.doublepressback), Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//
                @Override
                public void run() {
                    mBackKeyPressed = false;
                    }
                }, 2000);
            return;
            }
        Log.d("Main BackPress","press");
        super.onBackPressed();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private Fragment[] fragments = new Fragment[3];
        private Fragment[] details = new Fragment[3];
        private boolean[] isDetailed ={false,false,false};
        private NewsContent.NewsItem newsItem;
        private CafeContent.CafeItem cafeItem;
        public boolean isDetail(int position){
            return isDetailed[position];
        }
        public void setDetails(int position,boolean flag){
            isDetailed[position]=flag;
        }
        public void passNewsItem(NewsContent.NewsItem test){
            this.newsItem =test;
        }
        public void passCafeItem(CafeContent.CafeItem test){
            this.cafeItem = test;
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
                return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d("inst",position+"");
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            String name = "android:switcher:" + container.getId() + ":" + position;
//            Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
//            if (fragment !=null){
//            Log.d("getfragment",fragment.toString());
//            transaction.remove(fragment);}
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            for (int i=0; i<3; i++){
//                Fragment fg = fragments[i];
//                if (fg == null) continue;
//                transaction.remove(fg);
//            }
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {

            Log.d("fragmentmanger",getSupportFragmentManager().toString());
            Log.d("getItem in ",position+"");
            //0th item is news list.
            if (position ==0) {
                if (isDetailed[0]){
                    details[0] = NewsDetail.newInstance(newsItem.id, newsItem.details);
                    return details[0];
                }
                if(fragments[0]!=null) return fragments[0];
                fragments[0] = NewsLIstFragment.newInstance(1);
                return fragments[0];

            };
            if (position == 1){
                if(isDetailed[1]){
                    details[1]=CafeDetail.newInstance(cafeItem.id, cafeItem.details);
                    return details[1];
                }
                if (fragments[1]!=null) return fragments[1];
                fragments[1]=CafeListFragment.newInstance(2);
                return fragments[1];

            }
            

            //1th item is menu list (TODO)


            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return LoginFragment.newInstance("test","test");
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.news);
                case 1:
                    return getString(R.string.menu);
                case 2:
                    return getString(R.string.me);
            }
            return null;
        }
    }
}
