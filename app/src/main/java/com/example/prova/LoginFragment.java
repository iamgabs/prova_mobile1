package com.example.prova;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.prova.databinding.FragmentLoginBinding;
import com.example.prova.model.Database;
import com.example.prova.model.UserDAO;
import com.example.prova.model.entity.User;

import static com.example.prova.Util.getInstanceOfDatabase;
import static com.example.prova.Util.createToast;

import java.util.concurrent.TimeUnit;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    Database db;
    UserDAO dao;
    int userId;


    public LoginFragment() { super(R.layout.fragment_login); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private boolean login(String cpf, String password) {
        if(!(cpf.equals("") && password.equals(""))) {
            if( cpf.length() == 11 && password.length() >=4 ){
                db = getInstanceOfDatabase(getContext());
                dao = db.userDAO();
                User user =  dao.getUserByCPF(cpf);
                if(user != null) {
                    if(user.password.equals(password)) {
                        // inform to application that user is logged
                        UserConfig.setAsLogged();
                        userId = user.id;
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // action login
        View button = binding.button;
        button.setOnClickListener(view1 -> {
            String cpf = String.valueOf(binding.editTextTextCpf.getText());
            String password = String.valueOf(binding.editTextTextPassword.getText());

            if(login(cpf, password)) {
                createToast(getContext(), "Logado com sucesso!");
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);

                getParentFragmentManager().setFragmentResult("userId", bundle);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {} finally {
                    Navigation.findNavController(view).navigate(R.id.mainFragment);
                }
            } else {
                createToast(getContext(), "Não foi possível logar!");
            }
        });

        // switch to signin screen fragment
        View btnSwitchToSigninFragment = binding.buttonSwitchToSignin;
        btnSwitchToSigninFragment.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_signinFragment));
    }

}
