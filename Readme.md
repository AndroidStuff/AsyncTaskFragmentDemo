# AsyncTaskFragmentDemo

## About
Project that demos how AsyncTask can be moved to Fragment so that the AsyncTask when started can continue without it dying in the middle because of Parent Activity's destruction.

## Things Worth Noting
* The AsyncTask does not have a corresponding UI

## Steps
1. Create your AsyncTaskFragment class named something like AsyncTaskFragment extending Fragment, the template for which is below:
```Java
public class AsyncTaskFragment extends Fragment {

    public interface AsyncTaskInitiator {
        void handleTaskUpdate(String message);
    }

    private AsyncTaskInitiator parentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (AsyncTaskInitiator) activity;
    }

    public void runAsyncTask(Integer... integers) {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(integers);
    }

    class MyAsyncTask extends AsyncTask<Params, Progress, Result> {

        @Override
        protected Void doInBackground(Params... params) {
            for (Integer param : params) {
                publishProgress(param.toString());
                //TODO
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            parentActivity.handleTaskUpdate(values[0]);
        }
    }

}
```

2. Define your AsyncTask class that extends AsyncTask<Params, Progress, Result> something like below:
```Java
class MyAsyncTask extends AsyncTask<Params, Progress, Result> {
        @Override
        protected Void doInBackground(Params... params) {
            for (Integer param : params) {
                publishProgress(param.toString());
                //TODO
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            parentActivity.handleTaskUpdate(values[0]);
        }
}
``` 

3. Inside that class define an interface something like below:
```Java
    public interface AsyncTaskInitiator {
        void handleTaskUpdate(String message);
    }
```

4. The Activity class that starts the AsyncTask should implement the interface defined in the previous step. See sample code below:
```Java
public class MainActivity extends Activity implements AsyncTaskInitiator {
    @Override
    public void handleTaskUpdate(String message) {
        //TODO: Update UI
    }
}
```
