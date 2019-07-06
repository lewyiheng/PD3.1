package com.example.pd6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    public static final String LOG_TAG = ToDoAdapter.class.getName();

    private ArrayList<ToDo> alTodo;
    private Context context;

    public ToDoAdapter(Context context, int resource, ArrayList<ToDo> objects){
        super(context, resource, objects);
        alTodo = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.todo_row, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView tvDesc = (TextView) rowView.findViewById(R.id.tvDesc);

        ToDo todo = alTodo.get(position);

        tvTitle.setText(todo.getTitle());
        tvDesc.setText(todo.getDescription());

        return rowView;
    }
}
