package com.doraesol.dorandoran;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FamilyTreeFragment extends Fragment
{
    ImageView bg;
    PhotoViewAttacher mAttacher;

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
        //ButterKnife.bind(this, viewView);

        bg = (ImageView)viewView.findViewById(R.id.iv_fm_bg);
        mAttacher = new PhotoViewAttacher(bg);

        return viewView;
    }
}
