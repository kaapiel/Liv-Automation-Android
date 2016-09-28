package br.com.pontomobi.livelopontos.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.xml.transform.stream.StreamSource;

import br.com.pontomobi.livelopontos.model.NamedEntity;
import br.com.pontomobi.livelopontos.util.ConstantsServices;
import br.com.pontomobi.livelopontos.util.LerArquivo;
import br.com.pontomobi.livelopontos.util.SoapHelper;
import br.com.pontomobi.livelopontos.util.Utils;
import br.com.pontomobi.livelopontos.util.ViewConstants;

public class getTestContainers {

    public NamedEntity executar(long sessionid, int projectId) throws Exception {

        SoapObject request = new SoapObject("http://10.140.88.15:19120/Services1.0/services/tmplanning",
                "getTestContainers");
        request.addProperty("sessionId", sessionid);
        request.addProperty("projectId", projectId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);  //prepare request
        AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://10.140.88.15:19120/Services1.0/services/tmplanning?wsdl");
        httpTransport.debug = true;

        httpTransport.call("", envelope); //send request

        Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

        Log.e("CONTAINER", parseResponse(response).getName());

        return parseResponse(response);
    }

    private NamedEntity parseResponse(Vector<SoapObject> vec) {

        for (SoapObject so : vec) {
            NamedEntity ne = new NamedEntity();
            ne.setName(so.getProperty("name") != null ? so.getProperty("name").toString() : null);
            ne.setDescription(so.getProperty("description") != null ? so.getProperty("description").toString() : null);
            return ne;
        }
        return null;
    }

}
