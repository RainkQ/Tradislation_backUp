package tk.tnicy.tradisilation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.litepal.crud.DataSupport;
import tk.tnicy.tradisilation.Entity.BigType;
import tk.tnicy.tradisilation.Entity.SmallType;
import tk.tnicy.tradisilation.R;
import tk.tnicy.tradisilation.db.Translation;
import tk.tnicy.tradisilation.util.HttpUtil;
import tk.tnicy.tradisilation.util.TranslationAdapter;
import tk.tnicy.tradisilation.util.Utility;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends Activity {


    ArrayList<BigType> bigTypes;
    private List<Translation> translations;
    private List<Translation> translations_searched = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText editText;
    private ScrollView mScrollView;
    private TranslationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //获取控件
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.search_text);

        //按钮获取
        Button big_type_qin = findViewById(R.id.bigType_qin);
        Button big_type_qi = findViewById(R.id.bigType_qi);
        Button big_type_shu = findViewById(R.id.bigType_shu);
        Button big_type_hua = findViewById(R.id.bigType_hua);

        //获取translations
        queryTranslations();


        //分类存放在list中
        bigTypes = new ArrayList<>();
        for (Translation translation :
                translations) {
            boolean flag = false;
            for (BigType bigType : bigTypes) {
                if (bigType.typeName.equals(translation.getBigType())) {
                    flag = true;

                    boolean smallFlag = false;
                    for (SmallType smallType : bigType.smallTypes) {
                        if (smallType.typeName.equals(translation.getSmallType())) {
                            smallFlag = true;
                            smallType.translations.add(translation);
                        }
                    }
                    if (!smallFlag) {
                        bigType.smallTypes.add(new SmallType(translation.getSmallType(), new ArrayList<Translation>()));
                        bigType.smallTypes.get(bigType.smallTypes.size() - 1).translations.add(translation);
                    }

                }
            }
            if (!flag) {
                bigTypes.add(new BigType(translation.getBigType(), new ArrayList<SmallType>()));
                bigTypes.get(bigTypes.size() - 1).smallTypes.add(new SmallType(translation.getSmallType(), new ArrayList<Translation>()));
                bigTypes.get(bigTypes.size() - 1).smallTypes.get(0).translations.add(translation);
            }
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TranslationAdapter(translations);
        recyclerView.setAdapter(adapter);

        translations_searched.addAll(translations);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });





        //给四个按钮添加监听事件
        addListenersToButtons(big_type_qin, "琴");
        addListenersToButtons(big_type_qi, "棋");
        addListenersToButtons(big_type_shu, "书");
        addListenersToButtons(big_type_hua, "画");


    }

    private void queryTranslations() {
        translations = DataSupport.findAll(Translation.class);
        if (translations.size() == 0) {
            String address = "http://101.132.120.236:9999/";
            queryFromServer(address);
        }
    }

    private void queryFromServer(String address) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleJson(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        queryTranslations();
                    }
                });
            }
        });
    }

    private void search() {
        String data = editText.getText().toString().trim();
        if (!translations.isEmpty()) {
            // 清除原数据
            translations.clear();
        }
        // 对副List数据进行遍历匹配关键词
        for (Translation translation : translations_searched) {
            if ((translation.getChi() + translation.getEng()).contains(data)) {
                translations.add(translation);
            }
        }
        adapter.notifyDataSetChanged();
    }






    public void addListenersToButtons(Button button, final String cap) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BigTypeActivity.class);
                BigType bigType = null;
                for (BigType big : bigTypes) {
                    if (big.typeName.equals(cap)) {
                        bigType = big;
                    }
                }
                if (bigType == null) {
                    bigType = new BigType("", new ArrayList<SmallType>());
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("BigType", bigType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
