package com.doraesol.dorandoran;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.doraesol.dorandoran.map.MapMainActivity;
import com.doraesol.dorandoran.social.CmtBoardWriteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FamilyTreeFragment extends Fragment
{
    @BindView(R.id.sex) Button sex;
    public FamilyTreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_tree, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.sex)
    public void onbButtonClicked(View view){
        switch(view.getId()){
            case R.id.sex:
                startActivity(new Intent(getActivity(), MapMainActivity.class));
                break;
        }
    }
}