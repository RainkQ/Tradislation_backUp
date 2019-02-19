package tk.tnicy.tradisilation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import tk.tnicy.tradisilation.Entity.SmallType;
import tk.tnicy.tradisilation.R;
import tk.tnicy.tradisilation.db.Translation;

import java.util.ArrayList;

public class SmallTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_type);

        TextView title = findViewById(R.id.SmallType_Name);
        ListView listView = findViewById(R.id.smallType_list);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent lastIntent = getIntent();
        final SmallType smallType = (SmallType) lastIntent.getSerializableExtra("SmallType");

        title.setText(smallType.typeName);

        ArrayList<String> translations_string = new ArrayList<>();

        for (Translation translation :
                smallType.translations) {
            translations_string.add(translation.getChi());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SmallTypeActivity.this, android.R.layout.simple_list_item_1,translations_string);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SmallTypeActivity.this, DetailActivity.class);
                Bundle nextBundle = new Bundle();
                nextBundle.putSerializable("translation", smallType.translations.get(position));
                intent.putExtras(nextBundle);
                startActivity(intent);


            }
        });

    }
}
