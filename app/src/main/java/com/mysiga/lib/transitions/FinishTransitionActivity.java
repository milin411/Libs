package com.mysiga.lib.transitions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mysiga.lib.R;

/**
 * Created by wilson.wu on 2017/2/22.
 */

public class FinishTransitionActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_transition);
        findViewById(R.id.start_slide_right_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.slide_right_out);
            }
        });
    }
}
