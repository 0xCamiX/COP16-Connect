package com.camix.cop16connect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import com.camix.cop16connect.R;
import com.camix.cop16connect.model.User;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(@NonNull Context context, @NonNull List<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        User user = getItem(position);

        TextView tvUserName = convertView.findViewById(R.id.tv_user_name);
        TextView tvUserEmail = convertView.findViewById(R.id.tv_user_email);
        TextView tvUserRole = convertView.findViewById(R.id.tv_user_role);

        assert user != null;
        tvUserName.setText(user.getUsername());
        tvUserEmail.setText(user.getEmail());
        tvUserRole.setText(user.getRole());

        return convertView;
    }
}