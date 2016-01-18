package elec.dev.notes;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Activity_NavDrawer extends Fragment {

    public static final String PREF_FILENAME = "uxPreference";
    public static final String KEY_LEARNED = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private View containerView;

    private Boolean mUserLearnedDrawer;
    private Boolean mFromSavedInstanceState;

    public Activity_NavDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = readFromPreferences(getActivity(),KEY_LEARNED, false);
        mFromSavedInstanceState = savedInstanceState != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    public void setUp(int fragmentID, final DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if(!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_LEARNED, true);
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if(!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_LEARNED, true);
                }
                getActivity().invalidateOptionsMenu();
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String prefName, Boolean prefValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(prefName, prefValue);
        editor.apply();
    }

    public static Boolean readFromPreferences(Context context, String prefName, Boolean defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILENAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(prefName, defaultValue);
    }
}
