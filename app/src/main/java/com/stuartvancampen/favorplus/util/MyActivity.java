package com.stuartvancampen.favorplus.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.background.FragmentAsyncTask;
import com.stuartvancampen.favorplus.background.TaskFragment;
import com.stuartvancampen.favorplus.login.LoginActivity;
import com.stuartvancampen.favorplus.session.AuthPreferences;

/**
 * Created by Stuart on 13/10/2015.
 */
public class MyActivity extends AppCompatActivity {

    private static final String TAG_TASK_FRAGMENT = "task_fragment_";
    private static final String TAG = MyActivity.class.getSimpleName();
    private static final String EXTRA_NUMBER_OF_TASK_FRAGMENTS = "extra_number_of_task_fragments";

    private int mFragmentTaskNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onLoadInstanceState(savedInstanceState);

        setContentView(getLayoutId());

        attachTaskFragments(savedInstanceState);

        IntentFilter logoutIntent = new IntentFilter();
        logoutIntent.addAction("com.stuartvancampen.myblog.logout");
        registerReceiver(mOnLogoutReceiver, logoutIntent);


    }

    public void startAsyncTask(FragmentAsyncTask task) {
        FragmentManager fm = getFragmentManager();
        TaskFragment fragment = new TaskFragment();
        fm.beginTransaction().add(fragment, TAG_TASK_FRAGMENT + String.valueOf(mFragmentTaskNumber++)).commit();

        fm.executePendingTransactions();
        fragment.attachTask(task).startTask();
    }

    private void attachTaskFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragmentTaskNumber = 0;
        }
        else {
            mFragmentTaskNumber = savedInstanceState.getInt(EXTRA_NUMBER_OF_TASK_FRAGMENTS, 0);

            FragmentManager fm = getFragmentManager();
            for (int i = 0; i < mFragmentTaskNumber; i++) {
                TaskFragment fragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT + String.valueOf(i));

                fm.beginTransaction().add(fragment, TAG_TASK_FRAGMENT).commit();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mOnLogoutReceiver);
    }

    private BroadcastReceiver mOnLogoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    protected int getLayoutId() {
        return R.layout.my_activity;
    }

    protected void onLoadInstanceState(Bundle savedInstanceState) {
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
        else if (id == R.id.menu_logout) {
            closeOptionsMenu();
            AuthPreferences.get().clearAuthToken();

            startActivity(LoginActivity.create(this));

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("com.stuartvancampen.myblog.logout");
            sendBroadcast(broadcastIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
