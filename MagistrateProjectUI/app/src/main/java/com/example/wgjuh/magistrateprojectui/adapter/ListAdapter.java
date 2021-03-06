package com.example.wgjuh.magistrateprojectui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.ArticleThemeValue;
import com.example.wgjuh.magistrateprojectui.Constants;
import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.fragments.AbstractFragment;
import com.example.wgjuh.magistrateprojectui.fragments.QuestionsFragment;
import com.example.wgjuh.magistrateprojectui.fragments.TestFragment;
import com.example.wgjuh.magistrateprojectui.fragments.TextbookFragment;
import com.example.wgjuh.magistrateprojectui.fragments.ThemesFragment;

import java.util.ArrayList;

/**
 * Created by wGJUH on 19.02.2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<ArticleThemeValue> items;
    AbstractFragment fragment;
    Boolean isTesting;
    int userId;
    public ListAdapter(AbstractFragment fragment, ArrayList<ArticleThemeValue>  items, boolean isTesting){
        Log.d(Constants.TAG,"ListAdpater: " + items.size());
        this.items = items;
        this.fragment = fragment;
        this.isTesting = isTesting;
    }
    public ListAdapter(AbstractFragment fragment, ArrayList<ArticleThemeValue>  items, boolean isTesting,int userId){
        Log.d(Constants.TAG,"ListAdpater: " + items.size());
        this.items = items;
        this.fragment = fragment;
        this.isTesting = isTesting;
        this.userId = userId;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(Constants.TAG,"onCreateViewHolder ");
        View v = null;
        if(fragment instanceof ThemesFragment || fragment instanceof  QuestionsFragment) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_themes_list_single_item, parent, false);
        }else if (fragment instanceof TextbookFragment){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_library_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(fragment, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(Constants.TAG,"onBindViewHolder ");
        holder.textView.setText(items.get(position).getArticleName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        AbstractFragment fragment;
        ViewHolder(AbstractFragment fragment, View itemView) {
            super(itemView);
            if(fragment instanceof ThemesFragment || fragment instanceof  QuestionsFragment) {
                textView = (TextView) itemView;
            }else if (fragment instanceof TextbookFragment){
                textView = (TextView) itemView.findViewById(R.id.single_book_text);
            }
            itemView.setOnClickListener(this);
            this.fragment = fragment;
        }

        @Override
        public void onClick(View v) {

            if (fragment instanceof ThemesFragment){
                if(!isTesting) {
                    QuestionsFragment questionsFragment = QuestionsFragment.newInstance(items.get(getAdapterPosition()).getId());
                    fragment.getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.content_user_frame, questionsFragment, QuestionsFragment.class.getName())
                            .addToBackStack(fragment.getTag())
                            .commit();
                }else{
                   TestFragment testFragment = TestFragment.newInstance(items.get(getAdapterPosition()).getId(), userId);
                    fragment.getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.content_user_frame, testFragment, TestFragment.class.getName())
                            .addToBackStack(fragment.getTag())
                            .commit();
                }
            }

        }
    }
}
