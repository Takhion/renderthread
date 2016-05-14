package me.eugeniomarletti.renderthread.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TestView testRenderThreadOff = (TestView) findViewById(R.id.test_off);
        final TestView testRenderThreadOn = (TestView) findViewById(R.id.test_on);
        testRenderThreadOn.setUseRenderThread(true);

        findViewById(R.id.button_test).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View button) {
                        startTest(button, testRenderThreadOff, testRenderThreadOn);
                    }
                });
    }

    private void startTest(final View button, TestView... testViews) {

        // start animation for 3s
        button.setEnabled(false);
        for (TestView testView : testViews) {
            testView.startAnimation(3000);
        }

        // after 1s -> pause UI thread (for 1s)
        button.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        pauseUiThread(1000);
                    }
                },
                1000);

        // after 3s -> re-enable button
        button.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        button.setEnabled(true);
                    }
                },
                3000);
    }

    void pauseUiThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Toast.makeText(this, "Pause interrupted", Toast.LENGTH_SHORT).show();
        }
    }
}
