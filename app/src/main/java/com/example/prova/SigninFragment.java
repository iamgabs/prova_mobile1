package com.example.prova;

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

public class SigninFragment extends Fragment {

    FragmentSigninBinding binding;

    public SigninFragment() { super(R.layout.fragment_signin); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // switch to login screen fragment
        View btnSwitchToLoginFragment = binding.buttonSwitchToLogin;
        btnSwitchToLoginFragment.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signinFragment_to_loginFragment));

    }

}
