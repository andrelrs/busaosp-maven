package br.com.caelum.ondeestaobusao.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.ondeestaobusao.activity.MainActivity;
import br.com.caelum.ondeestaobusao.activity.R;
import br.com.caelum.ondeestaobusao.adapter.PontosEOnibusAdapter;
import br.com.caelum.ondeestaobusao.model.Onibus;
import br.com.caelum.ondeestaobusao.model.Ponto;

public class PontosProximosFragment extends Fragment {
	/*private PontoExpandableListView pontosExpandableListView;*/

    private StickyListHeadersListView pontosListView;
    private ArrayList<Onibus> selecionados;
    private TextView actionModeCustomView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        actionModeCustomView = new TextView(getActivity());
        if (savedInstanceState == null) {
            this.selecionados = new ArrayList<Onibus>();
        } else {
            this.selecionados = (ArrayList<Onibus>) savedInstanceState.getSerializable("selecionados");
            actionModeCustomView.setText(String.valueOf(this.selecionados.size()));
        }

        View view = inflater.inflate(R.layout.onibuses_proximos, null);

        pontosListView = (StickyListHeadersListView) view.findViewById(R.id.listPonto);
        pontosListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        List<Ponto> pontos = ((MainActivity) getActivity()).getPontos();
        if (pontos!= null) {
            colocaNaTela(pontos);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("selecionados", selecionados);
    }

    public void colocaNaTela(final List<Ponto> pontos) {
        PontosEOnibusAdapter adapter = new PontosEOnibusAdapter(pontos, getActivity());
        pontosListView.setAdapter(adapter);
        pontosListView.setMultiChoiceModeListener(callback(adapter));
    }

    private AbsListView.MultiChoiceModeListener callback(final PontosEOnibusAdapter adapter) {
        return new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int position, long l, boolean checked) {
                Onibus onibus = (Onibus) adapter.getItem(position);
                if (checked) {
                    selecionados.add(onibus);
                } else {
                    selecionados.remove(onibus);
                }

                actionModeCustomView.setText(String.valueOf(selecionados.size()));
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.setCustomView(actionModeCustomView);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                selecionados.clear();
            }
        };
    }



}
