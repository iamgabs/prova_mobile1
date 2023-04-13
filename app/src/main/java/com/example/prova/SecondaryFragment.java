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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prova.databinding.FragmentMainBinding;
import com.example.prova.databinding.FragmentSecondaryBinding;
import com.example.prova.model.DBSingleton;
import com.example.prova.model.Database;
import com.example.prova.model.NotesDAO;
import com.example.prova.model.entity.Notes;

import org.w3c.dom.Text;

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

        // action clear all history
        View clearAll = binding.buttonCA;
        clearAll.setOnClickListener(view1 -> {
            dao.clearAll();
        });

        // insert all
        LinearLayout layout = binding.linearLayout2;

        List<Notes> listNotes = dao.getAllNotes();
        for(int i=0; i< listNotes.size(); i++){
            LinearLayout line = createNewLayout(getContext());
            line.setOrientation(LinearLayout.HORIZONTAL);


            TextView tv = createTextView(getContext());
            tv.setText(String.valueOf(listNotes.get(i).id)+String.valueOf(listNotes.get(i).title)+String.valueOf(listNotes.get(i).note));

            Button btn = createButton(getContext());
            int id = listNotes.get(i).id;

            line.addView(tv);
            line.addView(btn);

            layout.addView(line);

            btn.setId(id);
            btn.setOnClickListener(view1 -> {
                dao.deleteNote(id);
                layout.removeView(line);
            });
        }



    }
}