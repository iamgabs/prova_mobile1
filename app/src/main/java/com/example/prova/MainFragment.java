package com.example.prova;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
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
    DBSingleton object;
    Database db;
    NotesDAO dao;
    EditText editTextTitle;
    EditText editTextNote;


    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentMainBinding.inflate(inflater, container, false);
        getParentFragmentManager().setFragmentResultListener("p_notesID", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                note.id = bundle.getInt("p_notesID");
                updateEditTextFields();
            }
        });
        editTextTitle = binding.editTextTitle;
        editTextNote = binding.editTextNote;
        return binding.getRoot();
    }

    private void clearTexts(EditText title, EditText notes) {
        // clear texts
        note.id = 0;
        note.title = "";
        note.note = "";
        title.setText("");
        notes.setText("");
    }

    private Database getInstanceOfDatabase(Context context) {
        DBSingleton object  = DBSingleton.object.getInstance(context);
        return object.db;
    }

    private void updateEditTextFields() {
        if(note.id != 0){
            db = getInstanceOfDatabase(getContext());
            Notes temp = dao.getNoteById(note.id);
            editTextTitle.setText(temp.title);
            editTextNote.setText(temp.note);
        } else {
            clearTexts(editTextTitle, editTextNote);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = getInstanceOfDatabase(getContext());
        dao = db.notesDAO();

        updateEditTextFields();
        // button "Salvar"
        View btnSave = binding.buttonSave;
        btnSave.setOnClickListener(view1 -> {

            if(editTextNote.equals("") || editTextNote.equals("")) {
                return;
            } else if(note.id != 0){
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
                clearTexts(editTextTitle, editTextNote);
            } catch (Exception e) {
                Toast.makeText(getContext(), "impossivel deletar", Toast.LENGTH_SHORT).show();
            }

        });
    }
}