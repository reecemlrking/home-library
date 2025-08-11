package com.example.homelibrary;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GoogleBooksApiRequestExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
