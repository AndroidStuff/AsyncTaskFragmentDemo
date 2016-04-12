package com.example.asynctaskfragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 *
 * Note that this Fragment does not host an interface.
 * It's sole purpose is to host the Async Task/Activity.
 *
 */
public class AsyncTaskFragment extends Fragment {

	public interface AsyncTaskInitiator {
		void handleTaskUpdate(String message);
	}

	private AsyncTaskInitiator parentActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Controls weather or not this Fragment instance should be retained
		// across Activity re-creation (such as from a configuration change).
		// This can only be used with fragments not in the back stack.
		// If set TRUE, the fragment life-cycle will be slightly different
		// when an Activity is RE-CREATED: onCreate(..) and onDestroy() won't be called;
		// onAttach(), onActivityCreated, onDetach() will still be called.
		setRetainInstance(true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		parentActivity = (AsyncTaskInitiator) activity;
		Log.i(getClass().getSimpleName(), "attached");
	}

	public void runAsyncTask(Integer... integers) {
		MyAsyncTask myAsyncTask = new MyAsyncTask();
		myAsyncTask.execute(integers);
	}

	class MyAsyncTask extends AsyncTask<Integer, String, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			for (Integer param : params) {
				publishProgress(param.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			parentActivity.handleTaskUpdate(values[0]);
		}
	}

}
