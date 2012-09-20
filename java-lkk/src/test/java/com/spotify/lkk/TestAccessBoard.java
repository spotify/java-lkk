package com.spotify.lkk;

import com.spotify.lkk.client.LeanKitKanbanBoard;
import com.spotify.lkk.client.LeanKitKanbanSimpleClient;
import com.spotify.lkk.types.Card;

import junit.framework.TestCase;

public class TestAccessBoard extends TestCase {
	public void noTestAccess() throws ApiException {
		HttpDriver driver = new DefaultHttpDriver("spotify.leankitkanban.com", 80, "username", "password");
		DefaultLeanKitKanbanApi api = new DefaultLeanKitKanbanApi(driver);
		        
		LeanKitKanbanSimpleClient client = new LeanKitKanbanSimpleClient(api);
		//LeanKitKanbanBoard board = client.getBoard("EX LKK/Jira integration test board");
		LeanKitKanbanBoard board = client.getBoard(102729828);
		
		Card card = new Card();

		card.setType("Defect");
		card.setTitle("Oh My Goon");
		/*card.setExternalSystemName("JIRA");
		card.setExternalSystemUrl("http://example.com/tickets/1");*/
		        
		/* external card id is a board wide setting to make visible, same for urls */
		card.setExternalCardId("OT-124");

		board.addCard(card, "ToDo", 0);
	}
}
