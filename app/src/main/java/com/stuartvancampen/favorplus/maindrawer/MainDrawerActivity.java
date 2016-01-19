package com.stuartvancampen.favorplus.maindrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.background.AsyncJsonHTTPTask;
import com.stuartvancampen.favorplus.background.FragmentAsyncTaskCallbacks;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.transaction.Transaction;
import com.stuartvancampen.favorplus.user.FriendFragment;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.user.UserList;
import com.stuartvancampen.favorplus.util.MyActivity;
import com.stuartvancampen.favorplus.util.OnItemClickListener;
import com.stuartvancampen.favorplus.util.SerializableObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class MainDrawerActivity extends MyActivity implements FragmentAsyncTaskCallbacks<SerializableObject>, OnItemClickListener {

    private static final String TAG = MainDrawerActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private DrawerItemAdapter mFriendNameAdapter;
    private UserList mUserList;
    private LinearLayoutManager mLayoutManager;

    public static Intent create(Context context) {
        return new Intent(context, MainDrawerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_drawer);

        startAsyncTask(new AsyncJsonHTTPTask<UserList>(UserList.class, null, UserList.getUrl(this), AsyncJsonHTTPTask.HttpVerb.GET));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);
        // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mDrawerList.setLayoutManager(mLayoutManager);

        // Set the adapter for the list view
        mFriendNameAdapter = new DrawerItemAdapter(this);
        mDrawerList.setAdapter(mFriendNameAdapter);
        // Set the list's click listener

        mUserList = new UserList();

        Fragment fragment = HomeFragment.create();

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

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
            Session.getInstance().setFriendList(mUserList);

            mFriendNameAdapter.addFriends(mUserList);
        }
        else if (result instanceof Transaction) {
            Transaction transaction = (Transaction) result;
            Log.d(TAG, transaction.toString());
            Toast.makeText(MainDrawerActivity.this, "transaction: " + transaction.toString() , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Fragment fragment = null;
        if (position == DrawerItemVH.ITEM_TYPE_HEADER) {
            //Nothing, maybe logout? settings?
        }
        else if (position == DrawerItemVH.ITEM_TYPE_HOME) {
            fragment = HomeFragment.create();
            setTitle("Favor Plus");
        }
        else if (position == DrawerItemVH.ITEM_TYPE_ADD_CONTACT) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_NEW_TRANSACTION) {
        }
        else if (position == DrawerItemVH.ITEM_TYPE_SUB_HEADER) {
            //Nothing
        }
        else if (position >= DrawerItemVH.ITEM_TYPE_FRIEND) {
            User user = mUserList.get(position - DrawerItemVH.ITEM_TYPE_FRIEND);
            if (user != null) {
                fragment = FriendFragment.create(mUserList.get(position - DrawerItemVH.ITEM_TYPE_FRIEND));
                setTitle(mUserList.get(position - DrawerItemVH.ITEM_TYPE_FRIEND).getFirstName());
            }
            else {
                Log.e(TAG, "could not find post at position " + String.valueOf(position - DrawerItemVH.ITEM_TYPE_FRIEND));
            }
        }
        else {
            throw new InvalidParameterException("unsupported type");
        }




        /*if (position == 0) {
            fragment = HomeFragment.create();
            setTitle("Favor Plus");
        }
        else {
            fragment = FriendFragment.create(mUserList.get(position - 1));
            setTitle(mUserList.get(position - 1).getFirstName());
        }*/

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        // Highlight the selected item, update the title, and close the drawer
        //mDrawerList.setItemChecked(position, true);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    public void selectItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = HomeFragment.create();
            setTitle("Favor Plus");
        }
        else {
            fragment = FriendFragment.create(mUserList.get(position - 1));
            setTitle(mUserList.get(position - 1).getFirstName());
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        //mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    /*@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }*/
}
