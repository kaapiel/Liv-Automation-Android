package br.com.pontomobi.livelopontos.service.livelo.userprofile.model;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by manoel0688 on 20/05/2016.
 */
public class ListUserAddressAdapter extends TypeAdapter {

    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }

    @Override
    public ListUserAddress read(JsonReader jsonReader) throws IOException {

        final ListUserAddress userAddress = new ListUserAddress();

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            UserAddressResponse userAddressItem;
            Gson gson = new Gson();
            String name = jsonReader.nextName();

            if (!name.contains("P:")) {
                userAddressItem = gson.fromJson(jsonReader, UserAddressResponse.class);
                userAddress.add(userAddressItem);
            } else {
                gson.fromJson(jsonReader, UserAddressResponse.class);
            }
        }

        jsonReader.endObject();

        return userAddress;
    }
}
