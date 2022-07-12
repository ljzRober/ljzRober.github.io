package com.ljz.plat.android;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class PermissionDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout constraintLayout = (ConstraintLayout) inflater.inflate(R.layout.permission_dialog, container, false);
        return constraintLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public PermissionDialog() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.permissionDialog);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ViewGroup decorView = (ViewGroup) requireDialog().getWindow().getDecorView();
        decorView.setPadding(decorView.getPaddingLeft(), 0, decorView.getPaddingRight(), 0);
        super.onActivityCreated(savedInstanceState);
        requireDialog().getWindow().getAttributes().gravity = Gravity.TOP;
        requireDialog().getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
    }
}
