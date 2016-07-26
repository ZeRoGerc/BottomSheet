package com.zerogerc.bottomsheetwithfixedbutton;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;

/**
 * Created by zerogerc on 26.07.16.
 */
public class CustomBottomSheetDialog extends BottomSheetDialogFragment {

    @Nullable
    private Button fixedButton;

    private final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case STATE_EXPANDED:
                    if (fixedButton != null) {
                        fixedButton.setTranslationY(0);
                    }
                    break;
                case STATE_HIDDEN:
                    dismiss();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            if (fixedButton != null) {
                float visibleHeight = bottomSheet.getHeight() * slideOffset;
                float diff = fixedButton.getHeight() - visibleHeight;
                fixedButton.setTranslationY(Math.max(0, diff));
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            BottomButtonProvider provider = ((BottomButtonProvider) getActivity());
            fixedButton = provider.getBottomButton();
        } catch (ClassCastException e) {
            fixedButton = null;
        }

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.content_bottom_sheet_dialog, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = ((CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams());
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(STATE_EXPANDED);
        }
        int x = 0;
    }

    @UiThread
    public interface BottomButtonProvider {
        @NonNull
        Button getBottomButton();
    }

}
