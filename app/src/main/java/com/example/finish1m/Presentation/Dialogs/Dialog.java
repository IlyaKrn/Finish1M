package com.example.finish1m.Presentation.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finish1m.R;

import java.util.ArrayList;

// абстрактный диалог

public abstract class Dialog extends Fragment {

    public static final String LOG_TAG = "FragmentTag";

    protected View rootView;
    protected FragmentManager fragmentManager;
    protected Context context;

    private static ArrayList<Dialog> currentDialogs = new ArrayList<>(); // текущие диалоги

    private OnDestroyListener onDestroyListener;

    private FragmentContainerView containerView;

    public Dialog(AppCompatActivity activity) {
        context = activity.getApplicationContext();
        fragmentManager = activity.getSupportFragmentManager();
    }

    public Dialog(Fragment fragment) {
        context = fragment.getActivity().getApplicationContext();
        fragmentManager = fragment.getChildFragmentManager();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView;

    }
    // создание и уничтожение диалога
    public void create(FragmentContainerView container){
        this.containerView = container;
        // уничтожение всех предыдущих диалогов
        for (Dialog d : currentDialogs) {
            d.destroy();
        }
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.dialog_create, R.anim.dialog_destroy).add(containerView.getId(), this).commit();
        currentDialogs.add(this);
    }
    public void destroy(){
        try {
            Animation animationDestroy = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_destroy);
            animationDestroy.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fragmentManager.beginTransaction().remove(Dialog.this).commit();
                    if (onDestroyListener != null) {
                        onDestroyListener.onDestroy();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            rootView.startAnimation(animationDestroy);

        } catch (Exception e){
            Log.w(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setClickable(containerView.getRootView(), false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setClickable(containerView.getRootView(), true);
    }

    public void setOnDestroyListener(OnDestroyListener onDestroyListener) {
        this.onDestroyListener = onDestroyListener;
    }
    // установка clickable на все элементы диалога
    public void freeze(){
        setClickable((ViewGroup) rootView, false);
    }
    protected void defreeze(){
        setClickable((ViewGroup) rootView, true);
    }
    private void setClickable(View view, boolean clickable) {
        if (view != null) {
            view.setClickable(clickable);
            if (view instanceof ViewGroup) {
                ViewGroup vg = ((ViewGroup) view);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    setClickable(vg.getChildAt(i), clickable);
                }
            }
        }
    }
}
