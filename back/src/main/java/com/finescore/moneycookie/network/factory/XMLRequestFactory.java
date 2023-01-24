package com.finescore.moneycookie.network.factory;

import com.finescore.moneycookie.models.ItemInfo;
import org.springframework.stereotype.Service;

@Service
public abstract class XMLRequestFactory<R> implements RequestFactory<R> {

    abstract String setURL(ItemInfo info);
}
