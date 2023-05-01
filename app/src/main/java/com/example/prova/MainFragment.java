package com.example.prova;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
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

import static com.example.prova.Util.getInstanceOfDatabase;
import static com.example.prova.Util.createToast;


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

        getParentFragmentManager().setFragmentResultListener("userId", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                note.userId = bundle.getInt("userId");
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

        if(!UserConfig.logged) {
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }

        db = getInstanceOfDatabase(getContext());
        dao = db.notesDAO();

        updateEditTextFields();
        // button "Salvar"
        View btnSave = binding.buttonSave;
        btnSave.setOnClickListener(view1 -> {

            if(!(editTextTitle.equals("") || editTextNote.equals(""))) {
                note.title = String.valueOf(editTextTitle.getText());
                note.note = String.valueOf(editTextNote.getText());
                if(note.id != 0){
                    // update note
                    dao.updateNote(note);
                    createToast(getContext(), "atualizado com sucesso!");
                    notify();
                } else {
                    // insert new note
                    dao.insertNewNote(note);
                    createToast(getContext(), "cadastrado com sucesso!");
                }
            }

            // clear texts
            clearTexts(editTextTitle, editTextNote);
        });

        // button "VER NOTAS"
        View btnSeeAll = binding.buttonSeeAll;
        btnSeeAll.setOnClickListener(view1 -> {

            Bundle bundle = new Bundle();
            bundle.putInt("userId", note.userId);

            getParentFragmentManager().setFragmentResult("userId", bundle);
            Navigation.findNavController(view).navigate(R.id.secondaryFragment);
        });

        // button "excluir"
        View btnDelete = binding.buttondelete;
        btnDelete.setOnClickListener(view1 -> {
            try {
                dao.deleteNote(note.id);
                clearTexts(editTextTitle, editTextNote);
                createToast(getContext(), "Nota deletada");
            } catch (Exception e) {
                createToast(getContext(), "Não foi possivel deletar esta nota");
            }

        });
    }
}