package com.example.tictactoegame.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.tictactoegame.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigHelper {
    private static final String TAG = "Helper";

    public static String getConfigValue(Context context, String name) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }

        return null;
    }
}