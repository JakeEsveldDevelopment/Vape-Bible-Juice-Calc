package com.jakeesveld.vapebiblejuicecalc.DAO;

import com.google.firebase.auth.FirebaseUser;

public class FirebaseDAO {
    public static FirebaseUser user;

    public static FirebaseUser getUser() {
        return user;
    }

    public static void setUser(FirebaseUser user) {
        FirebaseDAO.user = user;
    }
}
