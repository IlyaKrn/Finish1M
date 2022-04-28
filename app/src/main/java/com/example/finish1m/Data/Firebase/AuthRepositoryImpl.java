package com.example.finish1m.Data.Firebase;

import android.content.Context;

import com.example.finish1m.Domain.Interfaces.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    private Context context;
    public AuthRepositoryImpl(Context context) {
        this.context = context;
    }
}
