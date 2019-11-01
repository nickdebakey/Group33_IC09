package com.example.group33_ic09;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class InboxAdapter extends ArrayAdapter<Email> {
    TextView tv_subject, tv_date;


    public InboxAdapter(@NonNull Context context, int resource, @NonNull List<Email> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Email email = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inbox, parent, false);
        }

        tv_subject = convertView.findViewById(R.id.tv_subject);
        tv_date = convertView.findViewById(R.id.tv_date);

        tv_subject.setText(email.subject);
        tv_date.setText(email.date);

        return convertView;
    }
}
