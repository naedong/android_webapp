package kr.co.project.view.sign.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import kr.co.project.R;
import kr.co.project.databinding.LoadingBinding;

public class Loading extends Dialog {
    private ImageView imageView;
    public Loading(@NonNull Context context) {
        super(context);
        setContentView(R.layout.loading);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AnimationSet set = new AnimationSet(true);
        imageView = findViewById(R.id.spin_kit);
        Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate );
        imageView.startAnimation(myAnim);
        set.addAnimation(myAnim);
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }
}
