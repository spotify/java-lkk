package com.spotify.lkk;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.google.gson.reflect.TypeToken;
import com.spotify.lkk.types.AddCardRequest;
import com.spotify.lkk.types.AddCardResponse;
import com.spotify.lkk.types.BoardIdentifier;
import com.spotify.lkk.types.Card;
import com.spotify.lkk.types.ReplyData;

public class DefaultLeanKitKanbanApi implements LeanKitKanbanApi {
    final HttpDriver driver;

    final public static String ADD_CARD_URI = "/Kanban/Api/Board/%d/AddCard/Lane/%d/Position/%d";
    final public static String BOARD_IDENTIFIERS_URI = "/Kanban/Api/Board/%d/GetBoardIdentifiers";

    public DefaultLeanKitKanbanApi(HttpDriver driver)
    {        
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    private <T,R> List<T> execute(HttpUriRequest request, R entity, Type expected) throws HttpDriverException {
        final ReplyData<T> reply = (ReplyData<T>)this.driver.execute(request, entity, expected);

        if (reply.getReplyCode() / 100 != 2) {
            throw new HttpDriverRemoteException(reply.getReplyCode(), reply.getReplyText());
        }

        return reply.getReplyData();
    }

    private <T> List<T> get(String uri, Type expected) throws ApiException {
        final HttpGet request = new HttpGet(uri);

        try {
            return execute(request, null, expected);
        } catch (HttpDriverException e) {
            throw new ApiException(e);
        }
    }

    private <T, R> List<T> post(String uri, R entity, Type expected) throws ApiException {
        final HttpPost request = new HttpPost(uri);

        try {
            return execute(request, entity, expected);
        } catch (HttpDriverException e) {
            throw new ApiException(e);
        }
    }

    @Override
    public AddCardResponse addCard(Card card, int cardTypeId, int boardId, int laneId, int position) throws ApiException {
        final String uri = String.format(ADD_CARD_URI, boardId, laneId, position);

        final AddCardRequest addCard = new AddCardRequest();

        addCard.setTitle(card.getTitle());
        addCard.setDescription(card.getDescription());
        addCard.setTypeId(cardTypeId);
        addCard.setPriority(card.getPriority());
        addCard.setSize(card.getSize());
        addCard.setIsBlocked(false);
        addCard.setBlockReason(null);
        addCard.setDueDate(null);
        addCard.setExternalSystemName(card.getExternalSystemName());
        addCard.setExternalSystemUrl(card.getExternalSystemUrl());
        addCard.setExternalCardID(card.getExternalCardId());
        addCard.setTags(StringUtils.join(card.getTags(), ","));
        addCard.setClassOfServiceId(0);
        addCard.setAssignedUserIds(null);

        final Type expected = new TypeToken<ReplyData<AddCardResponse>>(){}.getType();
        final List<AddCardResponse> response = post(uri, addCard, expected);

        return response.get(0);
    }

    @Override
    public List<BoardIdentifier> getBoardIdentifiers(int boardId) throws ApiException {
        final String uri = String.format(BOARD_IDENTIFIERS_URI, boardId);
        final Type expected = new TypeToken<ReplyData<BoardIdentifier>>() {}.getType();
        return get(uri, expected);
    }
}
