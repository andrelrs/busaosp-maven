package br.com.caelum.ondeestaobusao.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import br.com.caelum.ondeestaobusao.activity.R;
import br.com.caelum.ondeestaobusao.model.Onibus;
import br.com.caelum.ondeestaobusao.model.Ponto;

public class PontosEOnibusAdapter extends BaseAdapter implements StickyListHeadersAdapter {
	private final Activity activity;
	private final List<PontosEOnibus> pontosEOnibuses;

	public PontosEOnibusAdapter(List<Ponto> pontos, Activity activity) {
		this.activity = activity;
        this.pontosEOnibuses = convert(pontos);
	}

    @Override
    public View getHeaderView(int i, View convertView, ViewGroup viewGroup) {
        PontosEOnibus pontosEOnibus = pontosEOnibuses.get(i);

        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_list_de_pontos, null);
            holder = new HeaderViewHolder();
            holder.nomePonto = (TextView) convertView.findViewById(R.id.nomePonto);
            holder.distancia = (TextView) convertView.findViewById(R.id.distancia);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.nomePonto.setText(pontosEOnibus.ponto.getDescricao());
        holder.distancia.setText(pontosEOnibus.ponto.getDistancia());

       return convertView;
    }

    @Override
    public long getHeaderId(int i) {
        return pontosEOnibuses.get(i).ponto.hashCode();
    }

    @Override
    public int getCount() {
        return pontosEOnibuses.size();
    }

    @Override
    public Object getItem(int i) {
        return pontosEOnibuses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pontosEOnibuses.hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Onibus onibus = pontosEOnibuses.get(i).onibus;

        TextView view = (TextView) convertView;
        if (view  == null)
            view = (TextView) activity.getLayoutInflater().inflate(R.layout.item_onibus, null);

        view.setText(onibus.toString());

        return view;
    }

    class HeaderViewHolder {
        TextView nomePonto;
        TextView distancia;
    }

    class PontosEOnibus {
        private Onibus onibus;
        private Ponto ponto;

        public PontosEOnibus(Onibus onibus, Ponto ponto) {
            this.onibus = onibus;
            this.ponto = ponto;
        }

    }

    private List<PontosEOnibus> convert(List<Ponto> pontos) {
        ArrayList<PontosEOnibus> pontosEOnibuses = new ArrayList<PontosEOnibus>();
        for (Ponto ponto : pontos) {
            for (Onibus onibus : ponto.getOnibuses()) {
                pontosEOnibuses.add(new PontosEOnibus(onibus, ponto));
            }
        }

        return pontosEOnibuses;
    }

    /*	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		Onibus onibus = pontos.get(groupPosition).getOnibuses().get(childPosition);
		
		TextView view = (TextView) activity.getLayoutInflater().inflate(R.layout.item_onibus, null);
		view.setText(onibus.toString());
		
		return view;
	}*/


/*	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		Ponto ponto = pontos.get(groupPosition);
		
		View view = activity.getLayoutInflater().inflate(R.layout.item_list_de_pontos, null);
		
		TextView nomePonto = (TextView) view.findViewById(R.id.nomePonto);
		TextView distancia = (TextView) view.findViewById(R.id.distancia);
		
		nomePonto.setText(ponto.getDescricao());
		distancia.setText(ponto.getDistancia());
		
		if(isExpanded) {
			view.setBackgroundResource(R.drawable.fundo_lista_pontos_e_onibuses);
		}
		
		return view;
	}*/

}
