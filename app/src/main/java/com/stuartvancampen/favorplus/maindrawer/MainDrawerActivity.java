package com.stuartvancampen.favorplus.maindrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.background.AsyncJsonHTTPTask;
import com.stuartvancampen.favorplus.background.FragmentAsyncTaskCallbacks;
import com.stuartvancampen.favorplus.transaction.Transaction;
import com.stuartvancampen.favorplus.user.FriendFragment;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.user.UserList;
import com.stuartvancampen.favorplus.util.MyActivity;
import com.stuartvancampen.favorplus.util.SerializableObject;

import java.util.ArrayList;
import java.util.List;

public class MainDrawerActivity extends MyActivity implements FragmentAsyncTaskCallbacks<SerializableObject> {

    private static final String TAG = MainDrawerActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mFriendNameAdapter;
    private UserList mUserList;

    public static Intent create(Context context) {
        return new Intent(context, MainDrawerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_drawer);

        startAsyncTask(new AsyncJsonHTTPTask<UserList>(UserList.class, null, UserList.getUrl(this), AsyncJsonHTTPTask.HttpVerb.GET));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mFriendNameAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, R.id.friend_name);
        mDrawerList.setAdapter(mFriendNameAdapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mUserList = new UserList();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(int percent) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(SerializableObject result) {
        if (result instanceof UserList) {
            mUserList = (UserList)result;
            List<String> friendsNames = new ArrayList<>();

            for (User friend : mUserList) {
                friendsNames.add(friend.getFirstName());
            }

            mFriendNameAdapter.addAll(friendsNames);
        }
        else if (result instanceof Transaction) {
            Transaction transaction = (Transaction) result;
            Log.d(TAG, transaction.toString());
            Toast.makeText(MainDrawerActivity.this, "transaction: " + transaction.toString() , Toast.LENGTH_SHORT).show();
        }
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
        /** Swaps fragments in the main content view */
        private void selectItem(int position) {
            // Create a new fragment and specify the planet to show based on position
            Fragment fragment = FriendFragment.create(mUserList.get(position));

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mUserList.get(position).getFirstName());
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    /*@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }*/
}
