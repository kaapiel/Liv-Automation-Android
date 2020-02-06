package br.com.aguido.livautomation.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.util.Vector;

import br.com.aguido.livautomation.model.NamedEntity;

public class getTestContainers {

    public NamedEntity executar(long sessionid, int projectId) throws Exception {

        SoapObject request = new SoapObject("http://192.168.0.1:19120/Services1.0/services/tmplanning",
                "getTestContainers");
        request.addProperty("sessionId", sessionid);
        request.addProperty("projectId", projectId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);  //prepare request
        AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://192.168.0.1:19120/Services1.0/services/tmplanning?wsdl");
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
