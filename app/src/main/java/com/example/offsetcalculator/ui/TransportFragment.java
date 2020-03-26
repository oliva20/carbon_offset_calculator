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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.offsetcalculator.R;
import com.example.offsetcalculator.model.workers.LocationWorker;
import com.example.offsetcalculator.rep.AirEmissionRepository;
import com.example.offsetcalculator.rep.BusEmissionRepository;
import com.example.offsetcalculator.rep.CarEmissionRepository;

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
    Button insert;
    PeriodicWorkRequest pWorkRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        carRep = new CarEmissionRepository(getActivity().getApplication());
        busRep = new BusEmissionRepository(getActivity().getApplication());
        airRep = new AirEmissionRepository(getActivity().getApplication());

        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        //worker
        pWorkRequest = new PeriodicWorkRequest.Builder(LocationWorker.class, 10, TimeUnit.MINUTES).build();
        //click listeners
        insert = (Button) view.findViewById(R.id.transport_btn1);
        insert.setOnClickListener(this);

        map = (MapView) view.findViewById(R.id.map1);
        map.getTileProvider().clearTileCache();
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(16.0);
        map.getController().setCenter(new GeoPoint(41.42, -9.3));

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.transport_btn1){
            WorkManager.getInstance().enqueueUniquePeriodicWork(
                "trackLocation",
                ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                pWorkRequest //work request
            );

            WorkManager.getInstance(getContext()).

            if(){
                insert.setText(R.string.btnStartTracking);
            } else {
                //only set the button to stop tracking if the work request hasn't failed
                insert.setText(R.string.btnStopTracking);
            }

        }
    }
}