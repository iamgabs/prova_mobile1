package com.example.prova;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prova.databinding.FragmentMainBinding;
import com.example.prova.model.DBSingleton;
import com.example.prova.model.Database;
import com.example.prova.model.NotesDAO;
import com.example.prova.model.entity.Notes;


public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    Notes note = new Notes();

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void clearTexts(EditText title, EditText note) {
        // clear texts
        title.setText("");
        note.setText("");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editTextTitle = binding.editTextTitle;
        EditText editTextNote = binding.editTextNote;

        DBSingleton object = DBSingleton.object.getInstance(getContext());;
        Database db = object.db;
        NotesDAO dao = db.notesDAO();

//        if(note.id != 0) {
//            Notes temp = dao.getNoteById(getArguments().getInt("NotesId"));
//            note.id = temp.id;
//            note.title = temp.title;
//            note.note = temp.note;
//
//            editTextTitle.setText(note.title);
//            editTextNote.setText(note.note);
//        }


        // button "Salvar"
        View btnSave = binding.buttonSave;
        btnSave.setOnClickListener(view1 -> {

            if(editTextNote.equals("") || editTextNote.equals("")) {
                return;
            } else if(note.id != 0){
                dao.updateNote(note);

                note.title = String.valueOf(editTextTitle.getText());
                note.note = String.valueOf(editTextNote.getText());

                dao.updateNote(note);

                // clear texts
                clearTexts(editTextTitle, editTextNote);
            } else {
                // insert new note
                note.title = String.valueOf(editTextTitle.getText());
                note.note = String.valueOf(editTextNote.getText());

                dao.insertNewNote(note);

                // clear texts
                clearTexts(editTextTitle, editTextNote);
            }
        });

        // button "VER NOTAS"
        View btnSeeAll = binding.buttonSeeAll;
        btnSeeAll.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_secondaryFragment));

        // button "excluir"
        View btnDelete = binding.buttondelete;
        btnDelete.setOnClickListener(view1 -> {
            try {
                dao.deleteNote(note.id);
            } catch (Exception e) {
                Toast.makeText(getContext(), "impossivel deletar", Toast.LENGTH_SHORT).show();
            }

        });
    }
}