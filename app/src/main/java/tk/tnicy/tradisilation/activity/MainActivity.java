package tk.tnicy.tradisilation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.litepal.crud.DataSupport;
import tk.tnicy.tradisilation.R;
import tk.tnicy.tradisilation.db.Translation;
import tk.tnicy.tradisilation.util.AndroidBug5497Workaround;
import tk.tnicy.tradisilation.util.HttpUtil;
import tk.tnicy.tradisilation.util.TranslationAdapter;
import tk.tnicy.tradisilation.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends Activity {


    private List<Translation> translations;

    private List<Translation> translations_searched = new ArrayList<>();

    List<String> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText editText;
    private ScrollView mScrollView;
    private TranslationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.search_text);


        queryTranslations();

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


//        AndroidBug5497Workaround.assistActivity(this);


    }


    private void queryTranslations(){
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
                        Toast.makeText(getContext(),"获取失败",Toast.LENGTH_SHORT).show();
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
        if(!translations.isEmpty()){
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



}
