package br.com.pontomobi.livelopontos.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.util.ArrayList;
import java.util.Vector;

import br.com.pontomobi.livelopontos.model.ExecutionNode;
import br.com.pontomobi.livelopontos.model.PropertyValue;

public class getExecutionRootNode {

    public ExecutionNode executar(long sessionid, int projectId) throws Exception {

        SoapObject request = new SoapObject("http://10.140.88.15:19120/Services1.0/services/tmexecution",
                "getExecutionRootNode");
        request.addProperty("sessionId", sessionid);
        request.addProperty("projectId", projectId);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);  //prepare request
        AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://10.140.88.15:19120/Services1.0/services/tmexecution?wsdl");
        httpTransport.debug = true;

        httpTransport.call("", envelope); //send request

        SoapObject response = (SoapObject) envelope.getResponse();

        Log.e("EXECUTIONNODE", parseResponse(response).getPv().get(0).getName());

        return parseResponse(response);

    }

    private ExecutionNode parseResponse(SoapObject so) {

        ExecutionNode en = new ExecutionNode();
        en.setId(Integer.valueOf(so.getProperty("id").toString()));
        en.setKind(Integer.valueOf(so.getProperty("kind").toString()));

        Vector<SoapObject> pvs = (Vector<SoapObject>) so.getProperty("propertyValues");
        ArrayList<PropertyValue> pv = new ArrayList<>();

        for (SoapObject soapObject : pvs) {
            PropertyValue propertyValue = new PropertyValue();

            propertyValue.setModifyCount(soapObject.getProperty("modifyCount") != null ? Integer.valueOf(soapObject.getProperty("modifyCount").toString()) : 0);
            propertyValue.setName(soapObject.getProperty("name") != null ? soapObject.getProperty("name").toString() : null);
            propertyValue.setNodeID(soapObject.getProperty("nodeID") != null ? soapObject.getProperty("nodeID").toString() : null);
            propertyValue.setPropertyID(soapObject.getProperty("propertyID") != null ? soapObject.getProperty("propertyID").toString() : null);
            propertyValue.setPropertyTypeID(soapObject.getProperty("propertyTypeID") != null ? soapObject.getProperty("propertyTypeID").toString() : null);
            propertyValue.setType(soapObject.getProperty("type") != null ? Integer.valueOf(soapObject.getProperty("type").toString()) : 0);
            propertyValue.setValue(soapObject.getProperty("value") != null ? soapObject.getProperty("value").toString() : null);

            Vector<SoapObject> children = (Vector<SoapObject>) soapObject.getProperty("children");
            ArrayList<String> strings = new ArrayList<>();

            for (SoapObject s : children) {
                strings.add(s.toString() != null ? s.toString() : null);
            }
            propertyValue.setChildren(strings);
            pv.add(propertyValue);
        }

        en.setPv(pv);

        return en;
    }

}
