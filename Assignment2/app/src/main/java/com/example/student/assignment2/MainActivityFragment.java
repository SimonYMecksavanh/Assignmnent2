package com.example.student.assignment2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView notes;
    private Spinner spinner;
    private ArrayAdapter<Note> adapter;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        //Get the ListView
        notes = (ListView) root.findViewById(R.id.notes_ListView);
        spinner = (Spinner) root.findViewById(R.id.sort_Spinner);

        //Populate spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource( this.getContext(),
                R.array.sort_choices, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerAdapter);


        //Create and initialize the adapter
        adapter = new NoteDataAdapter(this.getContext());
        notes.setAdapter(adapter);

        NoteDatabaseHandler dbh = new NoteDatabaseHandler(getContext());
        final List<Note> data;
        try{
            data = dbh.getNoteTable().readAll();
            adapter.addAll(data);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if (parent.getSelectedItem() == "Title") {
                    Toast.makeText(getContext(), "tegdgdfgfgdg", Toast.LENGTH_SHORT).show();

                    Collections.sort(data, new Comparator<Note>() {

                        @Override
                        public int compare(Note o1, Note o2) {
                            Toast.makeText(getContext(), "hdgifhojfdskhfdghjlkfhgjjkfdhklfdhkfshbdfkhjvx", Toast.LENGTH_SHORT).show();
                            return o2.getTitle().compareTo(o1.getTitle());
                        }
                    });
                }

                if (parent.getSelectedItem() == "Category") {
                    Collections.sort(data, new Comparator<Note>() {
                        @Override
                        public int compare(Note o1, Note o2) {

                            Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
                            return o1.getCategory() - o2.getCategory();

                        }
                    });

                }

                if (parent.getSelectedItem() == "Creation Date") {
                    Collections.sort(data, new Comparator<Note>() {
                        @Override
                        public int compare(Note o1, Note o2) {

                            Toast.makeText(getContext(), "gooddd", Toast.LENGTH_SHORT).show();
                            return o2.getCreated().compareTo(o1.getCreated());
                        }
                    });

                }

                if (parent.getSelectedItem() == "Reminder") {
                    Collections.sort(data, new Comparator<Note>() {
                        @Override
                        public int compare(Note o1, Note o2) {

                            if(o1.isHasReminder() == true && o2.isHasReminder() == false)
                                return -1;
                            else if (o2.isHasReminder() == true && o1.isHasReminder() == false)
                                return 1;
                            else
                                return o2.getReminder().compareTo(o1.getReminder());
                        }
                    });

                }
                adapter.clear();
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), NoteData.getData().toString(), Toast.LENGTH_SHORT).show();
            }
        });

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