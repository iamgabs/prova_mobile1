package com.example.prova;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prova.databinding.FragmentMainBinding;
import com.example.prova.databinding.FragmentSecondaryBinding;
import com.example.prova.model.DBSingleton;
import com.example.prova.model.Database;
import com.example.prova.model.NotesDAO;
import com.example.prova.model.entity.Notes;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SecondaryFragment extends Fragment {

    FragmentSecondaryBinding binding;

    public SecondaryFragment() {
        super(R.layout.fragment_secondary);
    }

    private TextView createTextView(Context context) {
        TextView tv = new TextView(context);
        tv.setWidth(300);
        return tv;
    }

    private LinearLayout createNewLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setMinimumWidth(View.MEASURED_SIZE_MASK);
        return layout;
    }

    private Button createButton(Context context){
        Button btn = new Button(context);
        btn.setText("X");
        return btn;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentSecondaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBSingleton object = DBSingleton.object.getInstance(getContext());;
        Database db = object.db;
        NotesDAO dao = db.notesDAO();

        // go back action
        View btnGoBack = binding.buttonGoBack;
        btnGoBack.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_secondaryFragment_to_mainFragment));

        // insert all
        ArrayList<Notes> listNotes = (ArrayList<Notes>) dao.getAllNotes();
        ArrayList<String> notes = new ArrayList<String>();

        for(Notes notations : listNotes) {
            String s = notations.title + "" + notations.note;
            notes.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                notes);

        ListView listView = (ListView) binding.listView;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i + 1;
                Bundle bundle = new Bundle();
                bundle.putInt("notesID", listNotes.get(i).id);
                Intent intent = new Intent(getContext(), MainFragment.class);
                intent.putExtras(bundle);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Substituir o fragmento atual pelo próximo fragmento
//                        getParentFragmentManager().beginTransaction()
//                                .replace(android.R.id.content, new MainFragment())
//                                .commit();
//                    }
//                }, 0);
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