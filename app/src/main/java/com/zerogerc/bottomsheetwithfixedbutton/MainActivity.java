package com.zerogerc.bottomsheetwithfixedbutton;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;

public class MainActivity extends AppCompatActivity
    implements CustomBottomSheetDialog.BottomButtonProvider {

    @SuppressWarnings("NullableProblems") // Butterknife
    @BindView(R.id.invoke_nested)
    @NonNull
    Button nestedButton;

    @SuppressWarnings("NullableProblems") // Butterknife
    @BindView(R.id.invoke_dialog)
    @NonNull
    Button dialogButton;

    @SuppressWarnings("NullableProblems") // Butterknife
    @BindView(R.id.bottom_sheet)
    @NonNull
    View bottomSheet;

    @SuppressWarnings("NullableProblems") // Butterknife
    @BindView(R.id.fixed_button)
    @NonNull
    Button fixedButton;

    @SuppressWarnings("NullableProblems") // Butterknife
    @BindView(R.id.background)
    @NonNull
    ScrimLinearLayout background;

    @SuppressWarnings("NullableProblems") // onCreate
    @NonNull
    BottomSheetBehavior bottomSheetBehavior;

    @NonNull
    private final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case STATE_EXPANDED:
                    fixedButton.setTranslationY(0);
                default:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            float visibleHeight = bottomSheet.getHeight() * slideOffset;
            float diff = fixedButton.getHeight() - visibleHeight;
            fixedButton.setTranslationY(Math.max(0, diff));
            background.setOpacity(slideOffset / 2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        background.setScrimColor(ContextCompat.getColor(this, android.R.color.black));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.invoke_nested)
    public void onNestedButtonClicked() {
        bottomSheetBehavior.setState(STATE_EXPANDED);
    }

    @OnClick(R.id.invoke_dialog)
    public void onDiaolgButtonClicked() {
        BottomSheetDialogFragment fragment = new CustomBottomSheetDialog();
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    @NonNull
    @Override
    public Button getBottomButton() {
        return fixedButton;
    }
}
