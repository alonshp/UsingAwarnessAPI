package com.sample.foo.usingawarenessapi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.awareness.fence.FenceState;

/**
 * Created by Obaro on 22/07/2016.
 */
public class FenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "FenceBroadcastReceiver";
    private static long currentTime;
    private static long lastTime = -1;


    @Override
    public void onReceive(Context context, Intent intent) {
        FenceState fenceState = FenceState.extract(intent);

        Log.d(TAG, "Received a Fence Broadcast");

        if (TextUtils.equals(fenceState.getFenceKey(), FenceActivity.HEADPHONE_FENCE_KEY)) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    Log.i(TAG, "Received a FenceUpdate -  Headphones are plugged in.");


                    // check last time popup was show
                    currentTime = System.currentTimeMillis() - 10 * 1000;

                    if (lastTime == -1 || currentTime > lastTime) {
                        lastTime = currentTime + 10 * 1000;

                    final WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.gravity = Gravity.CENTER;
                    layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.alpha = 1.0f;
                    layoutParams.packageName = context.getPackageName();
                    layoutParams.buttonBrightness = 1f;
                    layoutParams.windowAnimations = android.R.style.Animation_Dialog;

                    final View view = View.inflate(context.getApplicationContext(), R.layout.popup, null);
                    Button close = (Button) view.findViewById(R.id.dismiss);
                    close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            manager.removeView(view);
                        }
                    });

                    manager.addView(view, layoutParams);
                    } else {
                        Log.i(TAG, "Received a FenceUpdate, but not in time!");
                    }
                    break;
                case FenceState.FALSE:
                    Log.i(TAG, "Received a FenceUpdate -  Headphones are NOT plugged in.");
                    Toast.makeText(context, "Your headphones are NOT plugged in",
                            Toast.LENGTH_LONG).show();
                    break;
                case FenceState.UNKNOWN:
                    Log.i(TAG, "Received a FenceUpdate -  The headphone fence is in an unknown state.");
                    break;
            }
        } else if (TextUtils.equals(fenceState.getFenceKey(),
                FenceActivity.HEADPHONE_AND_WALKING_FENCE_KEY)) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    // check last time popup was show
                    currentTime = System.currentTimeMillis() - 10 * 1000;

                    if (lastTime == -1 || currentTime > lastTime) {
                        lastTime = currentTime + 10 * 1000;

                        final WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                        layoutParams.gravity = Gravity.CENTER;
                        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                        layoutParams.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        layoutParams.alpha = 1.0f;
                        layoutParams.packageName = context.getPackageName();
                        layoutParams.buttonBrightness = 1f;
                        layoutParams.windowAnimations = android.R.style.Animation_Dialog;

                        final View view = View.inflate(context.getApplicationContext(), R.layout.popup, null);
                        Button close = (Button) view.findViewById(R.id.dismiss);
                        close.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                manager.removeView(view);
                            }
                        });

                        manager.addView(view, layoutParams);
                    } else {
                        Log.i(TAG, "Received a FenceUpdate, but not in time!");
                    }
                    break;
                case FenceState.FALSE:
                    Log.i(TAG, "Received a FenceUpdate -  Headphones are NOT plugged in, OR You are NOT walking.");
                    break;
                case FenceState.UNKNOWN:
                    Log.i(TAG, "Received a FenceUpdate -  The headphone fence is in an unknown state.");
                    break;
            }
        } else if (TextUtils.equals(fenceState.getFenceKey(),
                FenceActivity.HEADPHONE_OR_WALKING_FENCE_KEY)) {
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    Log.i(TAG, "Received a FenceUpdate -  Headphones are plugged in, OR You are walking.");
                    break;
                case FenceState.FALSE:
                    Log.i(TAG, "Received a FenceUpdate -  Headphones are NOT plugged in, AND You are NOT walking.");
                    break;
                case FenceState.UNKNOWN:
                    Log.i(TAG, "Received a FenceUpdate -  The headphone/walking fence is in an unknown state.");
                    break;
            }
        }
    }
}
