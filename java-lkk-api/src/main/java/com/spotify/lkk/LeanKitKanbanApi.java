package com.spotify.lkk;

import java.rmi.RemoteException;

import com.spotify.lkk.types.LeanKitKanbanCard;

public interface LeanKitKanbanApi {
	void createCard(String board, String lane, int position, LeanKitKanbanCard card) throws HttpDriverException;
}
