package com.spotify.lkk;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.spotify.lkk.types.LeanKitKanbanCard;

public class RemoteLeanKitKanbanApi implements LeanKitKanbanApi {
	final HttpDriver driver;
	
	public RemoteLeanKitKanbanApi(HttpDriver driver)
	{		
		this.driver = driver;
	}

	@Override
	public void createCard(String board, String lane, int position, LeanKitKanbanCard card) throws HttpDriverException {
		final String uri = String.format("/Kanban/Api/Board/{0}/AddCard/Lane/{1}/Position/{2}", board, lane, position);
		final String body = "{  }";
		this.driver.post(uri, body);
		throw new RuntimeException("not implemented");
	}
}
