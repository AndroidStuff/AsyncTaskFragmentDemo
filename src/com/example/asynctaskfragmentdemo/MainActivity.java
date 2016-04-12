package com.example.asynctaskfragmentdemo;

import com.example.asynctaskfragmentdemo.AsyncTaskFragment.AsyncTaskInitiator;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity implements AsyncTaskInitiator {

	private static final String ASYNC_TASK_FRAGMENT = "Tag4AsyncTaskFragment";
	private ScrollView scrollView;
	private TextView textView;
	private AsyncTaskFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scrollView = (ScrollView) findViewById(R.id.scrollView);
		textView = (TextView) findViewById(R.id.textView);
		textView.setText("");

		FragmentManager fragmentManager = getFragmentManager();

		//Check if the ASYNC_TASK_FRAGMENT is already in memory. If not create one.
		fragment = (AsyncTaskFragment) fragmentManager.findFragmentByTag(ASYNC_TASK_FRAGMENT);
		if (fragment == null) {
			fragment = new AsyncTaskFragment();
			fragmentManager.beginTransaction().add(fragment, ASYNC_TASK_FRAGMENT).commit();
			Log.i(getClass().getSimpleName(), "Fragment Created");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void displayMessage(String msg) {
		textView.append(msg + System.getProperty("line.separator"));
		scrollView.scrollTo(0, scrollView.getBottom());
	}

	// onClick Event Handler
	public void gameOn(View v) {
		fragment.runAsyncTask(fetchIntegerArray());
	}

	private Integer[] fetchIntegerArray() {
		Integer[] arr = new Integer[100];
		for (int i = 0; i < 100; i++) {
			arr[i] = i + 1;
		}
		return arr;
	}

	@Override
	public void handleTaskUpdate(String message) {
		Log.i(getClass().getSimpleName(), "handleTaskUpdate invoked with message : " + message);
		displayMessage(message);
	}

}
