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

    void filter(String text) {
        model.list.clear();
        if(text.isEmpty()){
            model.list.addAll(model.copy);
        } else{
            text = text.toLowerCase();
            for(Detail item: model.copy){
                if(item.country.toLowerCase().contains(text)){
                    model.list.add(item);
                }
            }
        }
        notifyDataSetChanged();
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
        holder.textCountry.setText(Countries.getString(context, detail.countryCode));
        holder.textTotalConfirmed.setText(
                Countries.getNumber(context, R.string.total_data, detail.totalConfirmed));
        holder.textTotalDeaths.setText(
                Countries.getNumber(context, R.string.total_data, detail.totalDeaths));
        holder.textTotalRecovered.setText(
                Countries.getNumber(context, R.string.total_data, detail.totalRecovered));
        holder.textNewConfirmed.setText(
                Countries.getNumber(context, R.string.new_data, detail.newConfirmed));
        holder.textNewDeaths.setText(
                Countries.getNumber(context, R.string.new_data, detail.newDeaths));
        holder.textNewRecovered.setText(
                Countries.getNumber(context, R.string.new_data, detail.newRecovered));
    }

    @Override
    public int getItemCount() {
        return model.list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textCountry;
        TextView textTotalConfirmed;
        TextView textTotalDeaths;
        TextView textTotalRecovered;
        TextView textNewConfirmed;
        TextView textNewDeaths;
        TextView textNewRecovered;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCountry = itemView.findViewById(R.id.textCountry);
            textTotalConfirmed = itemView.findViewById(R.id.textTotalConfirmed);
            textTotalDeaths = itemView.findViewById(R.id.textTotalDeaths);
            textTotalRecovered = itemView.findViewById(R.id.textTotalRecovered);
            textNewConfirmed = itemView.findViewById(R.id.textNewConfirmed);
            textNewDeaths = itemView.findViewById(R.id.textNewDeaths);
            textNewRecovered = itemView.findViewById(R.id.textNewRecovered);
        }
    }
}
