package com.doraesol.dorandoran.familytree;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.doraesol.dorandoran.FamilyTreeSearchActivity;
import com.doraesol.dorandoran.MainActivity;
import com.doraesol.dorandoran.R;
import com.doraesol.dorandoran.config.Server;
import com.github.clans.fab.FloatingActionMenu;

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
        initalizeFamilyTreeSetting();
        return view;
    }


    private void initalizeFamilyTreeSetting(){
        wv_familytree.setWebViewClient(new WebViewClient());
        wv_familytree.setWebChromeClient(new WebChromeClient());
        wv_familytree.addJavascriptInterface(new FamilyTreeJSCallBack(), "to_Android");

        WebSettings webSettings = wv_familytree.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);

        wv_familytree.loadUrl(Server.CONNETION_FAMILYTREE_UI);

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
                    }
                });

                tb_main_bar.getMenu().clear();

                Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_LONG).show();
            }
        });

        // 취소버튼
        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // 메인 다이얼로그 생성
        AlertDialog alert = alertdialog.create();
        // 아이콘 설정
        alert.setIcon(R.drawable.ic_list_genogram);
        // 타이틀
        alert.setTitle("가계도");
        // 다이얼로그 보기
        alert.show();




    }

    @OnClick({R.id.fab_familytree_insert_mode,
            R.id.fab_familytree_edit_mode,
            R.id.fab_familytree_search})
    public void onFabButtonClicked(View view){
        switch (view.getId()){
            case R.id.fab_familytree_insert_mode:
                currentMode = 0;
                //Toast.makeText(getContext(),"추가 모드 선택", Toast.LENGTH_SHORT).show();
                tb_main_bar.inflateMenu(R.menu.menu_family_tree);
                break;
            case R.id.fab_familytree_edit_mode:
                currentMode = 1;
                tb_main_bar.getMenu().clear();
                break;
            case R.id.fab_familytree_search:
                startActivity(new Intent(getActivity(), FamilyTreeSearchActivity.class));
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
        }
    }
