package com.example.prova;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.prova.databinding.FragmentMainBinding;
import com.example.prova.model.DBSingleton;
import com.example.prova.model.Database;
import com.example.prova.model.NotesDAO;
import com.example.prova.model.entity.Notes;


public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBSingleton object = DBSingleton.object.getInstance(getContext());;
        Database db = object.db;

        // button "Salvar"
        View btnSave = binding.buttonSave;
        btnSave.setOnClickListener(view1 -> {
            EditText editTextTitle = binding.editTextTitle;
            EditText editTextNote = binding.editTextNote;
            if(editTextNote.equals("") || editTextNote.equals("")){
                return;
            } else {
                // insert new note
                Notes note = new Notes();
                note.title = String.valueOf(editTextTitle.getText());
                note.note = String.valueOf(editTextTitle.getText());
                NotesDAO dao = db.notesDAO();
                dao.insertNewNote(note);
                // clear texts
                editTextTitle.setText("");
                editTextNote.setText("");
            }
        });

        // button "VER NOTAS"
        View btnSeeAll = binding.buttonSeeAll;
        btnSeeAll.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_secondaryFragment));
    }
}