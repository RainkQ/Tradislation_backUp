package tk.tnicy.tradisilation.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import tk.tnicy.tradisilation.R;
import tk.tnicy.tradisilation.db.Translation;
import tk.tnicy.tradisilation.util.GetImage;

import java.util.ArrayList;

import static tk.tnicy.tradisilation.util.GetImage.*;

public class DetailActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                init();
            }
        };
        new Thread(runnable).start();

    }

    private void init() {

        Translation translation = (Translation) getIntent().getSerializableExtra("translation");

        String translation_chi = translation.getChi();
        String translation_eng = translation.getEng();
        String translation_detail = translation.getDetail();

        String translation_bigType = translation.getBigType();
        String translation_smallType = translation.getSmallType();

        TextView item_chi = findViewById(R.id.item_chi);
        TextView item_eng = findViewById(R.id.item_eng);
        TextView item_detail = findViewById(R.id.item_detail);
        Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        item_chi.setText(translation_chi);
        item_eng.setText(translation_eng);
        item_detail.setText(translation_detail);


//        final ArrayList<String> imgUrls = getImg(translation_chi);


        final String img1;
        try {
            img1 = getHtml("http://101.132.120.236:9999/pic/" + translation_chi + "1");
            final byte[] imageByteArray1 = Base64.decode(img1, Base64.DEFAULT);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Glide.with(DetailActivity.this).load(imageByteArray1).asBitmap().into((ImageView) findViewById(R.id.img0));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String img2;
        try {
            img2 = getHtml("http://101.132.120.236:9999/pic/" + translation_chi + "2");
            final byte[] imageByteArray2 = Base64.decode(img2, Base64.DEFAULT);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Glide.with(DetailActivity.this).load(imageByteArray2).asBitmap().into((ImageView) findViewById(R.id.img1));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
