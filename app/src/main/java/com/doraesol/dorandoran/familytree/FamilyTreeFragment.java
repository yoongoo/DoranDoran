package com.doraesol.dorandoran.familytree;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.doraesol.dorandoran.ActivityResultEvent;
import com.doraesol.dorandoran.BusProvider;
import com.doraesol.dorandoran.FamilyTreeSearchActivity;
import com.doraesol.dorandoran.MainActivity;
import com.doraesol.dorandoran.R;
import com.doraesol.dorandoran.config.DataConfig;
import com.doraesol.dorandoran.config.ResultCode;
import com.doraesol.dorandoran.config.Server;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.otto.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyTreeFragment extends Fragment {

    @BindView(R.id.fam_familytree)
    FloatingActionMenu fam_familytree;
    @BindView(R.id.wv_familytree)
    WebView wv_familytree;

    Toolbar tb_main_bar;
    int currentMode = 0;


    public FamilyTreeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_tree, container, false);
        ButterKnife.bind(this, view);
        fam_familytree.setClosedOnTouchOutside(true);
        tb_main_bar = (Toolbar) getActivity().findViewById(R.id.tb_main_bar);
        initializeFamilyTreeSetting();
        BusProvider.getInstance().register(this);

        // 웹뷰가 초기화된 후 호출이 되어야 하기 때문에 1.5초간 딜레이를 줌 향후 개선해야 할 코드임
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String data = loadCurrentFamilyTreeInfo();
                executeJsFunction("toJS_PrintFamilyTree", data);
                Toast.makeText(getActivity().getApplicationContext(), "불러오기..", Toast.LENGTH_LONG).show();
            }
        }, 1500);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        BusProvider.getInstance().unregister(this);
    }

    private void initializeFamilyTreeSetting(){
        wv_familytree.setWebViewClient(new WebViewClient());
        wv_familytree.setWebChromeClient(new WebChromeClient());
        wv_familytree.addJavascriptInterface(new FamilyTreeJSCallBack(), "to_Android");

        WebSettings webSettings = wv_familytree.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);

        wv_familytree.loadUrl(Server.CONNETION_FAMILYTREE_UI);
        Log.d("chromium", "load url : " + Server.CONNETION_FAMILYTREE_UI);


        tb_main_bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menu_family_tree_save){
                    showSaveDlg();
                }
                return false;
            }
        });
    }

    private void showSaveDlg(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());

        // 다이얼로그 메세지
        alertdialog.setMessage("저장 하시겠습니까?");

        // 확인버튼
        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentMode = 1;
                wv_familytree.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("CALLBACK", "toJS_setFamilyTreeMode is triggered .. ");
                        wv_familytree.loadUrl("javascript:toJS_setFamilyTreeMode(" + currentMode + ")");
                    }});

                executeJsFunction("toJS_GetCurrentFamilyTreeInfo");

                tb_main_bar.getMenu().clear();
                //loadFamilyTreeFromJson();
                Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_LONG).show();
            }
        });

        // 취소버튼
        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.setIcon(R.drawable.ic_list_genogram);
        alert.setTitle("가계도");
        alert.show();
    }

    private void saveCurrentFamilyTreeInfo(String paramJsonData){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(DataConfig.FAMILY_TREE_INFO, paramJsonData);
        editor.commit();
    }

    private String loadCurrentFamilyTreeInfo(){
        String retJsonData;
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        retJsonData = mPref.getString(DataConfig.FAMILY_TREE_INFO, DataConfig.EMPTY_DATA);

        if(retJsonData.equals(DataConfig.EMPTY_DATA)){
            Log.d("JSONDATA", "invalid Json Data .. ");
        }
        else {
            Log.d("JSONDATA", "valid json data .. ");
        }

        return retJsonData;
    }


    // Activity 에서 Fragment로 통신
    @Subscribe
    public void onActivityResultFromFamilyTreeNodeInfo(ActivityResultEvent event){
        String id       = "";
        String name     = "";
        String age      = "";
        String gender   = "";
        String relation = "";
        String phone    = "";
        String birth    = "";

        if(event.getResultCode() == ResultCode.ACK_RESULT_SUCCESS){
            id = event.getData().getExtras().getString("id");
            name = event.getData().getExtras().getString("name");
            age = event.getData().getExtras().getString("age");
            gender = event.getData().getExtras().getString("gender");
            relation = event.getData().getExtras().getString("relation");
            phone = event.getData().getExtras().getString("phone");
            birth = event.getData().getExtras().getString("birth");

            executeJsFunction("toJS_SetSelecetedMemberInfo", name, age, gender, relation);

            //Log.d("ACTIVITY_RESULT", id +","+name +"," +age+","+gender+","+relation+","+phone+","+birth);
        }
    }

    @OnClick({R.id.fab_familytree_insert_mode,
            R.id.fab_familytree_edit_mode,
            R.id.fab_familytree_search,
            R.id.fab_familytree_backup})
    public void onFabButtonClicked(View view){
        tb_main_bar.getMenu().clear();

        switch (view.getId()){
            // 노드 추가 모드
            case R.id.fab_familytree_insert_mode:
                currentMode = 0;
                //Toast.makeText(getContext(),"추가 모드 선택", Toast.LENGTH_SHORT).show();
                tb_main_bar.inflateMenu(R.menu.menu_family_tree);
                break;
            // 노드 수정 모드
            case R.id.fab_familytree_edit_mode:
                currentMode = 1;
                break;
            // 인맥 찾기
            case R.id.fab_familytree_search:
                startActivity(new Intent(getActivity(), FamilyTreeSearchActivity.class));
                break;

            // 가계도 백업
            case R.id.fab_familytree_backup:
                FamilyTreeBackupHelper familyTreeBackupHelper =
                        new FamilyTreeBackupHelper(getContext());
                boolean isSucceed = false;
                String backupData = loadCurrentFamilyTreeInfo();

                // AES256 메세지 암, 복호화
                String encryptedData = familyTreeBackupHelper.encryptedMessageFromAES256("1111", backupData);
                String decrpytedData = familyTreeBackupHelper.decryptedMessageFromAES256("1111", encryptedData);

                isSucceed = familyTreeBackupHelper.saveBackupFile(encryptedData);

                if(isSucceed){
                    Toast.makeText(getActivity().getApplicationContext(), "데이터 백업 성공", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "데이터 백업 실패", Toast.LENGTH_SHORT).show();
                }

                familyTreeBackupHelper.getFileNameList();


                break;
        }

        fam_familytree.close(true);

        wv_familytree.post(new Runnable() {
            @Override
            public void run() {
                Log.d("CALLBACK", "toJS_setFamilyTreeMode is triggered .. ");
                wv_familytree.loadUrl("javascript:toJS_setFamilyTreeMode(" + currentMode + ")");
            }
        });
    }



    public void executeJsFunction(String paramFuncName, Object... params){

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:")
                .append(paramFuncName)
                .append("(");

       for(int i=0; i<params.length; i++){
           if(i == params.length-1) {
               stringBuilder.append("'"+params[i].toString()+"'");
               break;
           }

           stringBuilder.append("'"+params[i]+"'").append(',');
       }
       stringBuilder.append(')');


        wv_familytree.post(new Runnable() {
            @Override
            public void run() {
                wv_familytree.loadUrl(stringBuilder.toString());
            }

        });


        Log.d("jsfunc", stringBuilder.toString());
    }

    private void loadFamilyTreeFromJson(String data){
        executeJsFunction("loadFamilyTreeView", data);
    }

    class FamilyTreeJSCallBack {
        // 추가 모드 - 노드 클릭
        @JavascriptInterface
        public void onFamilyTreeNodeClicked(String selectedNodeName){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("선택된 노드 : " + selectedNodeName)
                        .setItems(R.array.nodelist, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, final int which) {
                                // JavaScript 메서드는 반드시 메인 스레드에서 호출
                                wv_familytree.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        wv_familytree.loadUrl("javascript:toJS_AddMember(" + which + ")");
                                    }
                                });
                            }
                        });
                // inject TableLayout
                /*
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dlg_family_tree
                        = inflater.inflate(R.layout.dlg_family_tree_insert_node, null);
                builder.setView(dlg_family_tree);
                */
                builder.show();
        }

        @JavascriptInterface
        public void getFamilyTreeNodeInfo(String paramName, String paramAge, String paramGender, String paramRelation, String paramImage){

                // id, name, age, gender, relation, phone, birth
                Intent intent = new Intent(getContext(), FamilyTreeNodeInfo.class);
                intent.putExtra("name", paramName)
                        .putExtra("age", paramAge)
                        .putExtra("gender", paramGender)
                        .putExtra("relation", paramRelation)
                        .putExtra("image", paramImage);

                startActivity(intent);
        }

        @JavascriptInterface
        public void getCurrentFamilyTreeInfo(String retJsonData){
            saveCurrentFamilyTreeInfo(retJsonData);

            Log.d("JSONDATA", loadCurrentFamilyTreeInfo());
        }
        }
    }
