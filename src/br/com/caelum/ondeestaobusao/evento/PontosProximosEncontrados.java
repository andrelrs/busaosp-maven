package br.com.caelum.ondeestaobusao.evento;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import br.com.caelum.ondeestaobusao.evento.delegate.PontosEncontradosContextDelegate;
import br.com.caelum.ondeestaobusao.evento.delegate.PontosEncontradosDelegate;
import br.com.caelum.ondeestaobusao.model.Ponto;
import br.com.caelum.ondeestaobusao.util.MyLog;

public class PontosProximosEncontrados extends BroadcastReceiver implements Evento{

	private static final String FALHOU = "falhou";
	private static final String MENSAGEM_FALHA = "mensagemFalha";
	private static final String PONTOS = "pontos";
	private static final String PONTOS_ENCONTRADOS = "pontos-encontrados";
	private static final String PONTOS_NAO_ENCONTRADOS = "pontos-nao-encontrados";
	
	private PontosEncontradosDelegate delegate;

	@Override
	@SuppressWarnings("unchecked")
	public void onReceive(Context context, Intent intent) {
		if (intent.getBooleanExtra(FALHOU, false)) {
			MyLog.i("RECEBIDA MENSAGEM DE falha! para delegate"+delegate);
			
			delegate.lidaComFalha((String)intent.getSerializableExtra(MENSAGEM_FALHA));
		} else {
			MyLog.i("RECEBIDA MENSAGEM DE SUCESSO! para delegate"+delegate);
			ArrayList<Ponto> pontos = (ArrayList<Ponto>) intent.getSerializableExtra(PONTOS);			
			delegate.lidaComPontosProximos(pontos);
		}
		
	}
	public static void notifica(Context context, ArrayList<Ponto> pontos) {
		Intent intent = new Intent(PONTOS_ENCONTRADOS);

		intent.putExtra(PONTOS, pontos);
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
	
	public static Evento registraObservador(PontosEncontradosContextDelegate delegate){
		return registraObservador(delegate, delegate.getContext());
	}
	
	public static Evento registraObservador(PontosEncontradosDelegate delegate, Context context){
		PontosProximosEncontrados receiver = new PontosProximosEncontrados();
		receiver.delegate = delegate;
		
		LocalBroadcastManager.getInstance(context)
			.registerReceiver(receiver, new IntentFilter(PONTOS_ENCONTRADOS));
		
		LocalBroadcastManager.getInstance(context)
			.registerReceiver(receiver, new IntentFilter(PONTOS_NAO_ENCONTRADOS));
		
		return receiver;
	}
	public static void notificaFalha(Context context, String mensagem) {
		Intent intent = new Intent(PONTOS_NAO_ENCONTRADOS);
		intent.putExtra(MENSAGEM_FALHA, mensagem);
		intent.putExtra(FALHOU, true);
		
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
	
	public void unregister(Context context) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
	}
	
}
