package com.example.offsetcalculator.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.offsetcalculator.BuildConfig;
import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.workers.LocationWorker;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransportFragment extends Fragment implements View.OnClickListener {
    MapView map;
    CarEmissionRepository carRep;
    BusEmissionRepository busRep;
    AirEmissionRepository airRep;
    private Button insert;
    private Button stopTracking;
    PeriodicWorkRequest pWorkRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        carRep = new CarEmissionRepository(getActivity().getApplication());
        busRep = new BusEmissionRepository(getActivity().getApplication());
        airRep = new AirEmissionRepository(getActivity().getApplication());

        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        //location worker
        pWorkRequest = new PeriodicWorkRequest.Builder(LocationWorker.class, 10, TimeUnit.MINUTES).build();

        //click listeners
        insert = (Button) view.findViewById(R.id.transport_btn1);
        insert.setOnClickListener(this);
        stopTracking = (Button) view.findViewById(R.id.transport_btn2);
        stopTracking.setOnClickListener(this);

        initMap(view, new GeoPoint(37.25597166666667, -121.96105333333335));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.transport_btn1:
                WorkManager.getInstance(getContext()).enqueueUniquePeriodicWork(
                    "trackLocation",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    pWorkRequest); //work request
                break;
            case R.id.transport_btn2:
                WorkManager.getInstance(getContext()).cancelAllWork();
                Log.d("Button", "stop button called");
                break;
        }

    }

    private void initMap(View view, GeoPoint gp){
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        map = (MapView) view.findViewById(R.id.map1);
        map.getTileProvider().clearTileCache();
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(9.5);
        map.getController().setCenter(gp);
    }

}