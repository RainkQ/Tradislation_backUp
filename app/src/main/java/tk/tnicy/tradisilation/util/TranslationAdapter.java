package tk.tnicy.tradisilation.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import tk.tnicy.tradisilation.R;
import tk.tnicy.tradisilation.activity.DetailActivity;
import tk.tnicy.tradisilation.activity.MainActivity;
import tk.tnicy.tradisilation.db.Translation;

import java.util.List;



public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder{
        View translationView;
        TextView translation_chi;
        TextView translation_eng;

        public ViewHolder(View view){
            super(view);
            translationView = view;
            translation_chi = view.findViewById(R.id.translation_item_chi);
            translation_eng = view.findViewById(R.id.translation_item_eng);
        }


    }


    private Context mContext;
    private List<Translation> mTranslationList;


    public TranslationAdapter (List<Translation> translationList){
        mTranslationList = translationList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.translationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Translation translation = mTranslationList.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("translation_chi", translation.getChi());
                intent.putExtra("translation_eng", translation.getEng());
                intent.putExtra("translation_detail", translation.getDetail());
                intent.putExtra("translation_related", translation.getRelated());
                intent.putExtra("translation_bigType", translation.getBigType());
                intent.putExtra("translation_smallType", translation.getSmallType());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Translation translation = mTranslationList.get(position);
        viewHolder.translation_chi.setText(translation.getChi());
        viewHolder.translation_eng.setText(translation.getEng());

    }

    @Override
    public int getItemCount() {
        return mTranslationList.size();
    }




}
