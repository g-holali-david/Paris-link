package com.gavi.parislink;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;

    public CustomInfoWindowAdapter(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custom_info_window, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.snippet);

        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        /* View view = context.getLayoutInflater().inflate(R.layout.custom_info_window, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.snippet);

        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet()); */

        return null;
    }
}
