package com.doraesol.dorandoran;

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
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FamilyTreeFragment extends Fragment
{
    //PhotoViewAttacher mAttacher;
    FloatingActionButton fbt_plus, fbt_edit, fbt_father, fbt_mother;
    Button bt_add, bt_informedit, bt_delete, bt_ok;
    //@BindView(R.id.iv_fm_bg) ImageView bg;
    Animation FabOpen, FabClose, FabRClockwise, FabRanticlockwise;
    boolean isOpen = false;

    public FamilyTreeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View viewView = inflater.inflate(R.layout.fragment_family_tree, container, false);
        ButterKnife.bind(this, viewView);

        //핀치 줌인아웃
        //bg = (ImageView)viewView.findViewById(R.id.iv_fm_bg);
        //mAttacher = new PhotoViewAttacher(bg);

        fbt_plus = (FloatingActionButton) viewView.findViewById(R.id.fbt_plus);
        fbt_edit = (FloatingActionButton) viewView.findViewById(R.id.fbt_edit);
        fbt_father = (FloatingActionButton) viewView.findViewById(R.id.fbt_father);
        fbt_mother = (FloatingActionButton) viewView.findViewById(R.id.fbt_mother);
        bt_add = (Button) viewView.findViewById(R.id.bt_add);
        bt_informedit = (Button) viewView.findViewById(R.id.bt_informedit);
        bt_delete = (Button) viewView.findViewById(R.id.bt_delete);
        bt_ok = (Button) viewView.findViewById(R.id.bt_ok);
        FabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_clockwise);
        FabRanticlockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_anticlockwise);

        fbt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fbt_father.startAnimation(FabClose);
                    fbt_mother.startAnimation(FabClose);
                    fbt_edit.startAnimation(FabClose);
                    fbt_plus.startAnimation(FabRanticlockwise);
                    fbt_father.setClickable(false);
                    fbt_mother.setClickable(false);
                    fbt_edit.setClickable(false);
                    isOpen = false;
                } else
                {
                    fbt_father.startAnimation(FabOpen);
                    fbt_mother.startAnimation(FabOpen);
                    fbt_edit.startAnimation(FabOpen);
                    fbt_plus.startAnimation(FabRanticlockwise);
                    fbt_father.setClickable(true);
                    fbt_mother.setClickable(true);
                    fbt_edit.setClickable(true);
                    isOpen = true;
                }

            }
        });

        fbt_edit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                    fbt_father.startAnimation(FabClose);
                    fbt_mother.startAnimation(FabClose);
                    fbt_edit.startAnimation(FabClose);
                    bt_add.startAnimation(FabOpen);
                    bt_informedit.startAnimation(FabOpen);
                    bt_delete.startAnimation(FabOpen);
                    bt_ok.startAnimation(FabOpen);
                    fbt_plus.startAnimation(FabRanticlockwise);
                    fbt_father.setClickable(false);
                    fbt_mother.setClickable(false);
                    fbt_edit.setClickable(false);
                    bt_add.setClickable(true);
                    bt_informedit.setClickable(true);
                    bt_delete.setClickable(true);
                    bt_ok.setClickable(true);
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                bt_add.startAnimation(FabClose);
                bt_informedit.startAnimation(FabClose);
                bt_delete.startAnimation(FabClose);
                bt_ok.startAnimation(FabClose);
                bt_add.setClickable(false);
                bt_informedit.setClickable(false);
                bt_delete.setClickable(false);
                bt_ok.setClickable(false);
            }
        });
        return viewView;
    }
}
