package com.spotify.lkk;

import java.rmi.RemoteException;

import com.spotify.lkk.types.LeanKitKanbanCard;

public class RemoteLeanKitKanbanApi implements LeanKitKanbanApi {
	@Override
	public void createCard(LeanKitKanbanCard card) throws RemoteException {
		throw new RuntimeException("not implemented");
	}
}
