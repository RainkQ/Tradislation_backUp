package tk.tnicy.tradisilation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import tk.tnicy.tradisilation.Entity.BigType;
import tk.tnicy.tradisilation.Entity.SmallType;
import tk.tnicy.tradisilation.R;

import java.util.ArrayList;

public class BigTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_type);

        Intent lastIntent = getIntent();



        final BigType bigType = ((BigType) lastIntent.getSerializableExtra("BigType"));

        final ArrayList<String> smallTypes_string = new ArrayList<>();

        for (SmallType smallType :
                bigType.smallTypes) {
            smallTypes_string.add(smallType.typeName);
        }


        ListView listView = findViewById(R.id.bigType_list);
        TextView title = findViewById(R.id.BigType_Name);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setText(bigType.typeName);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BigTypeActivity.this, android.R.layout.simple_list_item_1, smallTypes_string);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BigTypeActivity.this, SmallTypeActivity.class);
                Bundle nextBundle = new Bundle();
                nextBundle.putSerializable("SmallType",bigType.smallTypes.get(position));

                intent.putExtras(nextBundle);

                startActivity(intent);

            }
        });




    }
}
