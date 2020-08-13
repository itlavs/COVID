package club.plus1.covid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<Item> list;

    public ListAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = list.get(position);
        holder.textCountry.setText(item.country);
        holder.textConfirmed.setText(context.getString(R.string.confirmed, item.newConfirmed, item.totalConfirmed));
        holder.textDeaths.setText(context.getString(R.string.deaths, item.newDeaths, item.totalDeaths));
        holder.textRecovered.setText(context.getString(R.string.recovered, item.newRecovered, item.totalRecovered));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
