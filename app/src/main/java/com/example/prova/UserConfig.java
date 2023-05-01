package com.example.prova;

public class UserConfig {
    public static Boolean logged = false;
    public static int userId = 0;

    public UserConfig() {}

    public static void setAsLogged() {
        logged = true;
    }

    public static void setLogout() {
        logged = false;
    }

}
