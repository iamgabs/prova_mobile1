package com.example.prova;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.prova.R;
import com.example.prova.databinding.FragmentSigninBinding;

import com.example.prova.model.Database;
import com.example.prova.model.UserDAO;
import com.example.prova.model.entity.User;

import static com.example.prova.Util.getInstanceOfDatabase;
import static com.example.prova.Util.createToast;


public class SigninFragment extends Fragment {

    FragmentSigninBinding binding;
    Database db;
    UserDAO dao;

    public SigninFragment() { super(R.layout.fragment_signin); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private User createUserObject(String name, String cpf, String password) {
        User user = new User();
        user.name = name;
        user.cpf = cpf;
        user.password = password;
        return user;
    }

    private boolean createNewUser(User u) {
        if(!(u.name.equals("") && u.cpf.equals("") && u.password.equals(""))) {
            if(u.cpf.length() == 11 && u.password.length() >= 4) {
                db = getInstanceOfDatabase(getContext());
                dao = db.userDAO();

                // insert new user
                dao.insertUser(u);
                return true;
            }
            return false;
        }
        return false;
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View button = binding.button;
        button.setOnClickListener(view1 -> {
            // get edittext fields
            String name = String.valueOf(binding.editTextTextPersonName.getText());
            String cpf = String.valueOf(binding.editTextTextCpf.getText());
            String password = String.valueOf(binding.editTextTextPassword.getText());

            // create user object
            User user = createUserObject(name, cpf, password);

            // create new user in database
            if(createNewUser(user)) {
                createToast(getContext(), "Usuário cadastrado com sucesso!");
                Navigation.findNavController(view).navigate(R.id.loginFragment);
            } else {
                createToast(getContext(), "Não foi possível cadastrar!");
            }

        });

        // switch to login screen fragment
        View btnSwitchToLoginFragment = binding.buttonSwitchToLogin;
        btnSwitchToLoginFragment.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signinFragment_to_loginFragment));

    }

}
