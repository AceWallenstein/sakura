package com.example.sakura.ui.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakura.R;
import com.example.sakura.adapter.DownloadAdapter;
import com.example.sakura.server.DownloadServer;

public class DownloadFragment extends Fragment {
    private static final String TAG = "DownloadFragment";

    private DownloadServer downloadServer;

    private RecyclerView rvProgress;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadServer = ((DownloadServer.MyBind)service).getDownloadServer()
           ;
            adapter.setData(downloadServer.getData());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private DownloadAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_download,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         rvProgress = view.findViewById(R.id.rv_progress);
         rvProgress.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new DownloadAdapter(getContext());
         rvProgress.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().bindService(new Intent(getActivity(), DownloadServer.class), conn, DownloadServer.BIND_AUTO_CREATE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(conn);
    }
}
