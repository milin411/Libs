package com.mysiga.lib.transitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mysiga.lib.R;

/**
 * Created by wilson.wu on 2017/2/22.
 */

public class StartTransitionActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_transition);
        findViewById(R.id.start_slide_right_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartTransitionActivity.this, FinishTransitionActivity.class));
                overridePendingTransition(R.anim.slide_right_in, R.anim.fade_out);
            }
        });
    }
}
