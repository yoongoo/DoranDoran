package com.doraesol.dorandoran.familytree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.doraesol.dorandoran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyTreeNodeInfo extends AppCompatActivity {

    @BindView(R.id.tv_family_tree_info_id)      TextView tv_family_tree_info_id;
    @BindView(R.id.tv_family_tree_info_name)    TextView tv_family_tree_info_name;
    @BindView(R.id.tv_family_tree_info_age)     TextView tv_family_tree_info_age;
    @BindView(R.id.tv_family_tree_info_gender)  TextView tv_family_tree_info_gender;
    @BindView(R.id.tv_family_tree_info_relation)TextView tv_family_tree_info_relation;
    @BindView(R.id.tv_family_tree_info_phone)   TextView tv_family_tree_info_phone;
    @BindView(R.id.tv__family_tree_info_birth)  TextView tv_family_tree_info_birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree_node_info);
        ButterKnife.bind(this);
        setNodeInfoFromIntent();
    }

    private void setNodeInfoFromIntent(){
        Intent intent = getIntent();
        // id, name, age, gender, relation, phone, birth
        String id ="";
        String name = intent.getExtras().getString("name");
        String age  = intent.getExtras().getString("age");
        String gender  = intent.getExtras().getString("gender");
        String relation  = intent.getExtras().getString("relation");
        String phone  = "";
        String birth  = "";

        tv_family_tree_info_id.setText(id);
        tv_family_tree_info_name.setText(name);
        tv_family_tree_info_age.setText(age);
        tv_family_tree_info_gender.setText(gender);
        tv_family_tree_info_relation.setText(relation);
        tv_family_tree_info_phone.setText(phone);
        tv_family_tree_info_birth.setText(birth);
    }

}
