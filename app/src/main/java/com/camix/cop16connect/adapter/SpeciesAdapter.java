package com.camix.cop16connect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camix.cop16connect.R;
import com.camix.cop16connect.model.Species;

import java.util.ArrayList;
import java.util.List;

public class SpeciesAdapter extends ArrayAdapter<Species> {

    private Context context;
    private List<Species> speciesList;

    public SpeciesAdapter(@NonNull Context context, @NonNull List<Species> speciesList) {
        super(context, 0, speciesList);
        this.context = context;
        this.speciesList = speciesList != null ? speciesList : new ArrayList<>();
    }

    private static class ViewHolder {
        TextView tvCommonName;
        TextView tvScientificName;
        TextView tvConservationStatus;
        TextView tvSpeciesDescription;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_species, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvCommonName = convertView.findViewById(R.id.tv_common_name);
            viewHolder.tvScientificName = convertView.findViewById(R.id.tv_scientific_name);
            viewHolder.tvConservationStatus = convertView.findViewById(R.id.tv_conservation_status);
            viewHolder.tvSpeciesDescription = convertView.findViewById(R.id.tv_species_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Species species = speciesList.get(position);

        viewHolder.tvCommonName.setText(species.getCommonName());
        viewHolder.tvScientificName.setText(species.getScientificName());
        viewHolder.tvConservationStatus.setText(species.getConservationStatus());
        viewHolder.tvSpeciesDescription.setText(species.getDescription());

        return convertView;
    }

    public void updateSpecies(List<Species> newSpeciesList) {
        speciesList.clear();
        speciesList.addAll(newSpeciesList);
        notifyDataSetChanged();
    }
}