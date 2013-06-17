package br.com.caelum.ondeestaobusao.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import br.com.caelum.ondeestaobusao.activity.R;
import br.com.caelum.ondeestaobusao.model.Onibus;
import br.com.caelum.ondeestaobusao.model.Ponto;
import android.widget.CheckBox;

public class PontosEOnibusAdapter extends BaseAdapter implements StickyListHeadersAdapter, View.OnClickListener {
	private final Activity activity;
	private final List<Onibus> onibuses;

	public PontosEOnibusAdapter(List<Ponto> pontos, Activity activity) {
		this.activity = activity;
        this.onibuses = convert(pontos);
	}

    @Override
    public View getHeaderView(int i, View convertView, ViewGroup viewGroup) {
        Onibus onibus = onibuses.get(i);

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

        holder.nomePonto.setText(onibus.getPonto().getDescricao());
        holder.distancia.setText(onibus.getPonto().getDistancia());

       return convertView;
    }

    @Override
    public long getHeaderId(int i) {
        return onibuses.get(i).getPonto().hashCode();
    }

    @Override
    public int getCount() {
        return onibuses.size();
    }

    @Override
    public Object getItem(int i) {
        return onibuses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return onibuses.get(i).hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Onibus onibus = onibuses.get(i);

        OnibusViewHolder holder;
        if (convertView  == null) {
            convertView = (View) activity.getLayoutInflater().inflate(R.layout.item_onibus, null);
            holder = new OnibusViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.nome_onibus);
            holder.selecionado = (CheckBox) convertView.findViewById(R.id.onibus_selecionado);
            convertView.setTag(holder);
        } else {
            holder = (OnibusViewHolder) convertView.getTag();
        }

        holder.selecionado.setOnClickListener(this);
        holder.nome.setText(onibus.toString());
        holder.selecionado.setTag(onibus);
        holder.selecionado.setChecked(onibus.iseFavorito());

        return convertView;
    }

    @Override
    public void onClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        Onibus onibus = (Onibus) view.getTag();

        onibus.seteFavorito(checkBox.isChecked());
    }

    class HeaderViewHolder {
        TextView nomePonto;
        TextView distancia;
    }

    public class OnibusViewHolder {
        private TextView nome;
        private CheckBox selecionado;

        public CheckBox getSelecionado() {
            return selecionado;
        }

        public TextView getNome() {
            return this.nome;
        }
    }

    private List<Onibus> convert(List<Ponto> pontos) {
        ArrayList<Onibus> onibuses = new ArrayList<Onibus>();
        for (Ponto ponto : pontos) {
            onibuses.addAll(ponto.getOnibuses());
        }

        return onibuses;
    }

}
