package br.com.caelum.ondeestaobusao.fragments;

import java.util.List;

import android.os.Bundle;
import android.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

import br.com.caelum.ondeestaobusao.activity.MainActivity;
import br.com.caelum.ondeestaobusao.activity.R;
import br.com.caelum.ondeestaobusao.activity.application.BusaoApplication;
import br.com.caelum.ondeestaobusao.adapter.PontosEOnibusAdapter;
import br.com.caelum.ondeestaobusao.model.Onibus;
import br.com.caelum.ondeestaobusao.model.Ponto;
import br.com.caelum.ondeestaobusao.task.BuscaPontosDoOnibusTask;
import br.com.caelum.ondeestaobusao.widget.PontoExpandableListView;

public class PontosProximosFragment extends Fragment {
	/*private PontoExpandableListView pontosExpandableListView;*/

    private StickyListHeadersListView pontosListView;
    private ActionMode actionMode;
    private int numero = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.onibuses_proximos, null);

        pontosListView = (StickyListHeadersListView) view.findViewById(R.id.listPonto);

        List<Ponto> pontos = ((MainActivity) getActivity()).getPontos();
		if (pontos!= null) {
			colocaNaTela(pontos);
		}

        final TextView textView = new TextView(getActivity());

        pontosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (actionMode == null) {
                    actionMode = getActivity().startActionMode(callback);

                    actionMode.setCustomView(textView);
                }


                view.setSelected(!view.isSelected());

                if (view.isSelected()) {
                    textView.setText(String.valueOf(numero++));

                } else {
                    textView.setText(String.valueOf(numero--));
                }
            }
        });
		
		return view;
	}
	
	public void colocaNaTela(final List<Ponto> pontos) {
        PontosEOnibusAdapter adapter = new PontosEOnibusAdapter(pontos, getActivity());
        pontosListView.setAdapter(adapter);
	}

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            PontosProximosFragment.this.actionMode = null;
            numero = 1;
        }
    };
	
}
