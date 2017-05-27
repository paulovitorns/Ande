package br.com.ande.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ande.R;
import br.com.ande.model.History;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {

    private List<History> historyList;
    private Context context;


    public HistoryAdapter(List<History> historyList, Context context) {
        this.historyList = historyList;
        this.context        = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(context).inflate(R.layout.row_history_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        History history = historyList.get(position);

        if(history != null){
            holder.dateDesc.setText(history.getDescriptionHistory());
            holder.distanceDesc.setText(history.getDescriptionDistance());
            holder.passos.setText(String.valueOf(history.getSteps()));
            holder.stepDescription.setText(history.getDescriptionSteps());
        }

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.txDateDesc)          TextView dateDesc;
        @Bind(R.id.txDistanceDesc)      TextView distanceDesc;
        @Bind(R.id.txPassos)            TextView passos;
        @Bind(R.id.txStepDescription)   TextView stepDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
