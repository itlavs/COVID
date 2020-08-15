package club.plus1.covid.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import club.plus1.covid.R;
import club.plus1.covid.data.Detail;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public Context context;
    ListModel model;
    static Handler handler;

    @SuppressLint("HandlerLeak")
    public ListAdapter(Context context, ListModel model) {
        this.context = context;
        this.model = model;
        handler = new Handler(){
            public void handleMessage(@NonNull Message message){
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail detail = model.list.get(position);
        holder.textCountry.setText(detail.country);
        holder.textConfirmed.setText(context.getString(
                R.string.confirmed, detail.newConfirmed, detail.totalConfirmed));
        holder.textDeaths.setText(context.getString(
                R.string.deaths, detail.newDeaths, detail.totalDeaths));
        holder.textRecovered.setText(context.getString(
                R.string.recovered, detail.newRecovered, detail.totalRecovered));
    }

    @Override
    public int getItemCount() {
        return model.list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textCountry;
        TextView textConfirmed;
        TextView textDeaths;
        TextView textRecovered;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCountry = itemView.findViewById(R.id.textCountry);
            textConfirmed = itemView.findViewById(R.id.textConfirmed);
            textDeaths = itemView.findViewById(R.id.textDeaths);
            textRecovered = itemView.findViewById(R.id.textRecovered);
        }
    }
}
