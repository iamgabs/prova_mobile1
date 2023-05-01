package com.example.prova;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import com.example.prova.databinding.FragmentSecondaryBinding;
import com.example.prova.model.Database;
import com.example.prova.model.NotesDAO;
import com.example.prova.model.entity.Notes;
import com.example.prova.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.prova.Util.getInstanceOfDatabase;

public class SecondaryFragment extends Fragment {

    FragmentSecondaryBinding binding;
    int userId;

    public SecondaryFragment() {
        super(R.layout.fragment_secondary);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentSecondaryBinding.inflate(inflater, container, false);

        getParentFragmentManager().setFragmentResultListener("userId", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                userId = bundle.getInt("userId");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!UserConfig.logged) {
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }

        if(userId == 0) {
            userId = UserConfig.userId;
        }

        Database db = getInstanceOfDatabase(getContext());
        NotesDAO dao = db.notesDAO();

        // go back action
        View btnGoBack = binding.buttonGoBack;
        btnGoBack.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_secondaryFragment_to_mainFragment));

        // insert all
        Map<User, List<Notes>> mappingNotesByUser = dao.getNotesByUser(userId);
        List<Notes> listNotes = new ArrayList<>();
        ArrayList<String> notes = new ArrayList<String>();


        for(Map.Entry<User, List<Notes>> map: mappingNotesByUser.entrySet()){
            List<Notes> userNotes = map.getValue();
            listNotes.addAll(userNotes);
        }

        for(Notes notations : listNotes) {
            String s = notations.title + " - " + notations.note;
            notes.add(s);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                notes);

        adapter.notifyDataSetChanged();

        ListView listView = (ListView) binding.listView;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i;
                Bundle bundle = new Bundle();
                bundle.putInt("p_notesID", listNotes.get(i).id);
                bundle.putInt("userId", userId);

                getParentFragmentManager().setFragmentResult("p_notesID", bundle);
                Navigation.findNavController(view).navigate(R.id.mainFragment);
            }
        });



        // action clear all history
        View clearAll = binding.buttonCA;
        clearAll.setOnClickListener(view1 -> {
            dao.clearAll();
            adapter.clear();
            adapter.notifyDataSetChanged();
        });


    }
}