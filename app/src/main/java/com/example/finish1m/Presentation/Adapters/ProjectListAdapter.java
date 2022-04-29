package com.example.finish1m.Presentation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.finish1m.Domain.Models.Project;
import com.example.finish1m.R;

import java.util.ArrayList;

public class ProjectListAdapter extends  Adapter<Project, ProjectListAdapter.ViewHolder>{
    public ProjectListAdapter(Activity activity, Context context, ArrayList<Project> items) {
        super(activity, context, items);
    }

    @Override
    protected ViewHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_project, parent, false));
    }

    public class ViewHolder extends Holder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(int position) {

        }
    }
}
