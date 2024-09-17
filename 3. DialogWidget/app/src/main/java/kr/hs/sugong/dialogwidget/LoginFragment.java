package kr.hs.sugong.dialogwidget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class LoginFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout,container);
        getDialog().setTitle("Login...");
        ImageView iv = (ImageView) view.findViewById(R.id.locker);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        iv.startAnimation(animation);
        return view;
    }
}

