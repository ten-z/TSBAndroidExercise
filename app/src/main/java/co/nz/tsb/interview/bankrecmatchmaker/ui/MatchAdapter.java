package co.nz.tsb.interview.bankrecmatchmaker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.nz.tsb.interview.bankrecmatchmaker.R;
import co.nz.tsb.interview.bankrecmatchmaker.data.MatchItem;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mainText;
        private TextView total;
        private TextView subtextLeft;
        private TextView subtextRight;

        public ViewHolder(View itemView) {
            super(itemView);
            mainText = itemView.findViewById(R.id.text_main);
            total = itemView.findViewById(R.id.text_total);
            subtextLeft = itemView.findViewById(R.id.text_sub_left);
            subtextRight = itemView.findViewById(R.id.text_sub_right);
        }

        public void bind(MatchItem matchItem) {
            mainText.setText(matchItem.getPaidTo());
            total.setText(matchItem.getTotal().toPlainString());
            subtextLeft.setText(matchItem.getTransactionDate());
            subtextRight.setText(matchItem.getDocType());
        }

    }

    public MatchAdapter() {
    }

    private OnItemClickListener onItemClickListener;
    private List<MatchItem> matchItems = new ArrayList<>();
    private List<MatchItem> selectedItems = new ArrayList<>();

    public void setItems(List<MatchItem> matchItems) {
        this.matchItems = matchItems;
        notifyDataSetChanged();
    }

    public void setSelectedItems(List<MatchItem> newSelectedItems) {
        //Only refresh the difference selectedItem
        for (int i = 0; i < matchItems.size(); i++) {
            MatchItem item = matchItems.get(i);
            boolean isSelected = selectedItems.contains(item);
            boolean newSelected = newSelectedItems.contains(item);
            if (isSelected != newSelected) {
                notifyItemChanged(i);
            }
        }

        selectedItems.clear();
        selectedItems.addAll(newSelectedItems);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CheckedListItem listItem = (CheckedListItem) layoutInflater.inflate(R.layout.list_item_match, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MatchItem matchItem = matchItems.get(position);
        holder.bind(matchItem);

        CheckedListItem checkedView = (CheckedListItem) holder.itemView;
        checkedView.setChecked(selectedItems.contains(matchItem));

        holder.itemView.setOnClickListener(v -> {
            CheckedListItem checkedListItem = (CheckedListItem) v;
            boolean isChecked = !checkedListItem.isChecked();

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(matchItem, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(MatchItem matchItem, boolean isChecked);
    }

}