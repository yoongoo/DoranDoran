package com.doraesol.dorandoran.familytree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doraesol.dorandoran.ActivityResultEvent;
import com.doraesol.dorandoran.BusProvider;
import com.doraesol.dorandoran.R;
import com.doraesol.dorandoran.config.ResultCode;
import com.doraesol.dorandoran.map.MapMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FamilyTreeNodeInfo extends AppCompatActivity {

    @BindView(R.id.tv_family_tree_info_id)      TextView tv_family_tree_info_id;
    @BindView(R.id.tv_family_tree_info_name)    TextView tv_family_tree_info_name;
    @BindView(R.id.tv_family_tree_info_age)     TextView tv_family_tree_info_age;
    @BindView(R.id.tv_family_tree_info_gender)  TextView tv_family_tree_info_gender;
    @BindView(R.id.tv_family_tree_info_relation)TextView tv_family_tree_info_relation;
    @BindView(R.id.tv_family_tree_info_phone)   TextView tv_family_tree_info_phone;
    @BindView(R.id.tv__family_tree_info_birth)  TextView tv_family_tree_info_birth;
    @BindView(R.id.fab_family_tree_info_pic)      FloatingActionButton fab_family_tree_info_pic;
    @BindView(R.id.fab_family_tree_info_graves)      FloatingActionButton fab_family_tree_info_graves;

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
        String id       = "";
        String name     = intent.getExtras().getString("name");
        String age      = intent.getExtras().getString("age");
        String gender   = intent.getExtras().getString("gender");
        String relation = intent.getExtras().getString("relation");
        String phone    = "";
        String birth    = "";

        tv_family_tree_info_id.setText(id);
        tv_family_tree_info_name.setText(name);
        tv_family_tree_info_age.setText(age);
        tv_family_tree_info_gender.setText(gender);
        tv_family_tree_info_relation.setText(relation);
        tv_family_tree_info_phone.setText(phone);
        tv_family_tree_info_birth.setText(birth);
    }

    @OnClick({R.id.ll_family_tree_info_id, R.id.ll_family_tree_info_name,
    R.id.ll_family_tree_info_age, R.id.ll_family_tree_info_gender,
    R.id.ll_family_tree_info_relation, R.id.ll_family_tree_info_phone,
    R.id.ll_family_tree_info_birth})
    public void onLayoutNodeInfoClicked(View paramView){
        int options = -1;
        switch (paramView.getId()){
            //case R.id.ll_family_tree_info_id:       options = 0;    break;
            case R.id.ll_family_tree_info_name:     options = 1;    break;
            case R.id.ll_family_tree_info_age:      options = 2;    break;
            case R.id.ll_family_tree_info_gender:   options = 3;    break;
            case R.id.ll_family_tree_info_relation: options = 4;    break;
            case R.id.ll_family_tree_info_phone:    options = 5;    break;
            case R.id.ll_family_tree_info_birth:    options = 6;    break;
        }

        showEditDialog(options);
    }

    @OnClick({R.id.iv_family_tree_info_confirm, R.id.iv_family_tree_info_back})
    public void onConfirmAndBack(View paramView){
        String id       = tv_family_tree_info_id.getText().toString();
        String name     = tv_family_tree_info_name.getText().toString();
        String age      = tv_family_tree_info_age.getText().toString();
        String gender   = tv_family_tree_info_gender.getText().toString();
        String relation = tv_family_tree_info_relation.getText().toString();
        String phone    = tv_family_tree_info_phone.getText().toString();
        String birth    = tv_family_tree_info_birth.getText().toString();

        switch (paramView.getId()){
            case R.id.iv_family_tree_info_confirm:

                try {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("age", age);
                    intent.putExtra("gender", gender);
                    intent.putExtra("relation", relation);
                    intent.putExtra("phone", phone);
                    intent.putExtra("birth", birth);
                    BusProvider.getInstance().post(new ActivityResultEvent(0, ResultCode.ACK_RESULT_SUCCESS, intent));
                    finish();
                }
                catch (Exception ex){
                    Log.d("ACTIVITY_RESULT", ex.toString());
                }
                break;

            case R.id.iv_family_tree_info_back:
                finish();
                break;
        }
    }


    private void showEditDialog(final int options) {

        final EditText et_modify = new EditText(this);
        et_modify.setBackgroundColor(Color.WHITE);
        et_modify.setHint("수정할 내용을 입력해주세요");
        et_modify.setPadding(100, 50, 0, 0);

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setView(et_modify);
        //alertdialog.setMessage("");

        alertdialog.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String retStr = et_modify.getText().toString();
                TextView retTextView = getTextViewFromIndex(options);
                retTextView.setText(retStr);

                Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();
            }
        });

        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        String retStr = getStringFromIndex(options);
        AlertDialog alert = alertdialog.create();
        alert.setIcon(R.drawable.ic_list_genogram);
        alert.setTitle("프로필 " + retStr + " 수정");
        alert.show();
    }


    private TextView getTextViewFromIndex(int options){

        TextView retTextView = null;

        switch (options){
            //case 0: retTextView = tv_family_tree_info_id;       break;
            case 1: retTextView = tv_family_tree_info_name;     break;
            case 2: retTextView = tv_family_tree_info_age;      break;
            case 3: retTextView = tv_family_tree_info_gender;   break;
            case 4: retTextView = tv_family_tree_info_relation; break;
            case 5: retTextView = tv_family_tree_info_phone;    break;
            case 6: retTextView = tv_family_tree_info_birth;    break;
        }

        return retTextView;
    }

    private String getStringFromIndex(int options){
        String retStr = null;

        switch (options){
            //case 0: retStr = "아이디";       break;
            case 1: retStr = "이름";      break;
            case 2: retStr = "나이";      break;
            case 3: retStr = "성별";      break;
            case 4: retStr = "관계";      break;
            case 5: retStr = "연락처";    break;
            case 6: retStr = "생년월일";  break;
        }


        return retStr;
    }

    @OnClick(R.id.fab_family_tree_info_pic)
    public void onUpdateProfilePicture(){
        printToast("사진 변경 클릭");
    }

    @OnClick(R.id.fab_family_tree_info_graves)
    public void onGoMap()
    {
        printToast("묘지 클릭");
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MapMainActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }
    private void printToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
