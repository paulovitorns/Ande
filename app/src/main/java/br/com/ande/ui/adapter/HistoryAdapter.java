package br.com.ande.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ande.R;
import br.com.ande.sqlLite.entity.Histoty;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {

    private List<Histoty> histotyList;
    private Context context;


    public HistoryAdapter(List<Histoty> histotyList, Context context) {
        this.histotyList    = histotyList;
        this.context        = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(context).inflate(R.layout.row_history_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Histoty histoty = histotyList.get(position);

        if(histoty != null){
            holder.steps.setText(String.valueOf(histoty.getSteps()));
            holder.duration.setText("0h e 40m");
        }

    }

    @Override
    public int getItemCount() {
        return histotyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.txPassos) TextView steps;
        @Bind(R.id.txTempo)  TextView duration;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
