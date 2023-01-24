package com.finescore.moneycookie.batch;

import com.finescore.moneycookie.network.generator.AllItemsGenerator;
import com.finescore.moneycookie.models.ItemInfo;
import com.finescore.moneycookie.models.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
@AllArgsConstructor
public class BatchFactory {
    private final AllItemsGenerator generator;

    public ResponseMessage<ItemInfo> getAllItems() throws IOException, ParserConfigurationException, SAXException {
        ResponseMessage<ItemInfo> respMessage = new ResponseMessage<>("OK");
        respMessage.setContents(generator.get());
        return respMessage;
    }
}
