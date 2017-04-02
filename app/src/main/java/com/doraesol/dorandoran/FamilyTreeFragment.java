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
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FamilyTreeFragment extends Fragment
{
    @BindView(R.id.fam_familytree)
    FloatingActionMenu fam_familytree;

    public FamilyTreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_tree, container, false);
        ButterKnife.bind(this, view);
        fam_familytree.setClosedOnTouchOutside(true);

        return view;
    }

    @OnClick({R.id.fab_familytree_chinga, R.id.fab_familytree_churga, R.id.fab_familytree_churweiga,
    R.id.fab_familytree_search, R.id.fab_familytree_weiga})
    public void FabButtonClicked(View view){
        switch (view.getId()){
            case R.id.fab_familytree_chinga:
                break;

            case R.id.fab_familytree_churga:
                break;

            case R.id.fab_familytree_churweiga:
                break;


            case R.id.fab_familytree_weiga:
                break;

            case R.id.fab_familytree_search:
                startActivity(new Intent(getActivity(), FamilyTreeSearchActivity.class));
                break;
        }
    }


}
