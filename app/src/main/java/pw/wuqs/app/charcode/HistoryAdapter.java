package pw.wuqs.app.charcode;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Qishen Wu on 2017-05-18.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";
    private List<CharCode> historyList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView asciiTextView;
        public TextView charTextView;
        public MyViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.historyCardView);
            asciiTextView = (TextView) v.findViewById(R.id.asciiTextView);
            charTextView = (TextView) v.findViewById(R.id.charTextView);
        }
    }

    public HistoryAdapter(List<CharCode> historyList) {
        this.historyList = historyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item_view, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e(TAG, "set value");
        holder.asciiTextView.setText(historyList.get(position).getDecUnicodeStr());
        holder.charTextView.setText(historyList.get(position).getCharStr());
        holder.cv.setTag(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
