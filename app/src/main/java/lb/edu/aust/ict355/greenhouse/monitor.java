package lb.edu.aust.ict355.greenhouse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class monitor extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "Monitor Activity";
    private StatesAdapter adapter;
    private RemoteAPI remoteAPI;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        swipeRefreshLayout = findViewById(R.id.main_swipeRefreshLayout);

        remoteAPI = new RemoteAPI();
        RecyclerView recyclerView = findViewById(R.id.main_recyclerView);
        adapter = new StatesAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        refresh();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh(){
            FetchStatesAsyncTask fetchStatesAsyncTask = new FetchStatesAsyncTask(remoteAPI, adapter, swipeRefreshLayout);
            fetchStatesAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static class FetchStatesAsyncTask extends AsyncTask<Void, Void, List<States>> {

        private final RemoteAPI remoteAPI;
        private final StatesAdapter adapter;
        // This is wrong, because it will cause memory leaks if something happens.
        // But I kept it simple just to facilitate your life
        @SuppressLint("StaticFieldLeak")
        private final SwipeRefreshLayout swipeRefreshLayout;

        public FetchStatesAsyncTask(RemoteAPI remoteAPI, StatesAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {

            this.remoteAPI = remoteAPI;
            this.adapter = adapter;
            this.swipeRefreshLayout = swipeRefreshLayout;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<States> doInBackground(Void... voids) {
            try {
                return remoteAPI.listStates();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<States> states) {

            super.onPostExecute(states);

            adapter.setItems(states);

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
