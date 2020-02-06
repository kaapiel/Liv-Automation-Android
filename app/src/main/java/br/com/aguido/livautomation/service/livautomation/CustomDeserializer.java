package br.com.aguido.livautomation.service.livautomation;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.aguido.livautomation.service.livautomation.checkout.model.CheckoutAddressResponse;
import br.com.aguido.livautomation.service.livautomation.checkout.model.ShippingAddress;

/**
 * Created by vilmar on 5/21/16.
 */
public class CustomDeserializer implements JsonDeserializer<CheckoutAddressResponse> {

    @Override
    public CheckoutAddressResponse deserialize(JsonElement json, Type typeOfT,
                                               JsonDeserializationContext context) throws JsonParseException {

        CheckoutAddressResponse checkoutAddressResponse = new CheckoutAddressResponse();
        Gson gson = new Gson();

        JsonObject object = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {

           if (entry.getKey().equals("shippingGroups")) {
               LinkedHashMap<String, ShippingAddress> result = new LinkedHashMap<>();

               for(Map.Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
                   String key = entry2.getKey();

                   String jsonElement = entry2.getValue().getAsJsonObject().get("shippingAddress").toString();
                   ShippingAddress value = gson.fromJson(jsonElement, ShippingAddress.class);

                   result.put(key, value);
               }

               checkoutAddressResponse.setShippingGroups(result);
           }
        }

        return checkoutAddressResponse;
    }

}
