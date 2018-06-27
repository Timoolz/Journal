package com.example.olamide.journal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olamide.journal.database.DiaryEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {


    private static final String DATE_FORMAT = "dd/MM/yyyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    private List<DiaryEntry> mDiaryEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public DiaryEntryAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public DiaryEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.diary_entry_layout, parent, false);

        return new DiaryEntryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DiaryEntryViewHolder holder, int position) {
        // Determine the values of the wanted data
        DiaryEntry diaryEntry = mDiaryEntries.get(position);

        String mood = diaryEntry.getMood();
        String diaryContent = diaryEntry.getDiaryContent();

        String[] lines = diaryContent.split(System.getProperty("line.separator"));
        String createdAt = dateFormat.format(diaryEntry.getCreatedAt());
        String updatedAt = dateFormat.format(diaryEntry.getUpdatedAt());

        //Set values
        holder.moodView.setText(mood);
        holder.diaryContentView.setText(lines[0]+ '\n' +lines[1] + '\n' +lines[2]+ '\n' +lines[3] +"...");

        holder.datesView.setText("Modified on " +updatedAt+ "  /  Created on " +createdAt);
    }



    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mDiaryEntries == null) {
            return 0;
        }
        return mDiaryEntries.size();
    }


    public List<DiaryEntry> getDiaryEntries() {
        return mDiaryEntries;
    }

    /**
     * When data changes, this method updates the list of diaryEntries
     * and notifies the adapter to use the new values on it
     */
    public void setDiaryEntries(List<DiaryEntry> diaryEntries) {
        mDiaryEntries = diaryEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    // Inner class for creating ViewHolders
    class DiaryEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView diaryContentView;
        TextView moodView;
        TextView datesView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public DiaryEntryViewHolder(View itemView) {
            super(itemView);

            diaryContentView = itemView.findViewById(R.id.diaryContent);
            moodView = itemView.findViewById(R.id.moodTextView);
            datesView = itemView.findViewById(R.id.diaryDates);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mDiaryEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }


}
