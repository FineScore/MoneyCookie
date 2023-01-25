package com.finescore.moneycookie.network.factory;

import com.finescore.moneycookie.models.ItemInfo;
import org.w3c.dom.Document;

public abstract class XMLRequestFactory implements RequestFactory<Document, ItemInfo> {

    abstract String setURL(ItemInfo info);
}
