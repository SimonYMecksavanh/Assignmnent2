package com.example.student.assignment2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView notes;
    private ArrayAdapter<Note> adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        notes = (ListView) root.findViewById(R.id.notes_ListView);

        adapter = new NoteDataAdapter(this.getContext());

        return root;
    }


    private class NoteDataAdapter extends ArrayAdapter<Note> {

        public NoteDataAdapter(Context context) {
            super(context, -1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //inflate or reuse previously inflated UI
            View root;
            if (convertView != null)
                root = convertView;
            else {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                root = inflater.inflate(R.layout.list_item_note, parent, false);
            }

            Note note = getItem(position);

            ImageView category = (ImageView) root.findViewById(R.id.category_ImageView);
            TextView title = (TextView) root.findViewById(R.id.title_TextView);
            TextView body = (TextView) root.findViewById(R.id.body_TextView);

            category.setBackgroundColor(note.getCategory());
            title.setText(note.getTitle());
            body.setText(note.getBody());


            return root;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId(); //Use Note IDs }
        }
    }

}