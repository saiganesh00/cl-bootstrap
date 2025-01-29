package com.example.clbootstrap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.clbootstrap.R;
import com.example.clbootstrap.models.Country;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {
    private final LayoutInflater inflater;

    public CountryAdapter(@NonNull Context context, @NonNull List<Country> countries) {
        super(context, 0, countries);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.item_country, parent, false);
            holder = new ViewHolder();
            holder.flagImage = view.findViewById(R.id.countryFlag);
            holder.nameText = view.findViewById(R.id.countryName);
            holder.codeText = view.findViewById(R.id.countryCode);
            holder.dialCodeText = view.findViewById(R.id.countryDialCode);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Country country = getItem(position);
        if (country != null) {
            holder.flagImage.setImageResource(country.getFlagResource());
            holder.nameText.setText(country.getName());
            holder.codeText.setText(country.getCode());
            holder.dialCodeText.setText(country.getDialCode());
        }

        return view;
    }

    private static class ViewHolder {
        ImageView flagImage;
        TextView nameText;
        TextView codeText;
        TextView dialCodeText;
    }
}
