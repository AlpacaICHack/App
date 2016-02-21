package com.alpaca.app;

import android.app.Activity;

import java.util.Calendar;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Util {
    public static long getTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static void createCrouton(Activity activity, String text) {
        Crouton.cancelAllCroutons();
        Style style = new Style.Builder()
                .setTextSize(15)
                .build();
        Crouton.makeText(activity, text, style).show();
    }
}
