package br.com.aguido.livautomation.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.util.Vector;

public class startExecutionTMEXECUTION {

	public void executar(long sessionid, int nodeId) throws Exception{

		SoapObject request = new SoapObject("http://192.168.0.1:19120/Services1.0/services/tmexecution",
				"startExecution");
		request.addProperty("sessionId", sessionid);
		request.addProperty("nodeId", nodeId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);  //prepare request
		AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://192.168.0.1:19120/Services1.0/services/tmexecution?wsdl");
		httpTransport.debug = true;

		httpTransport.call("", envelope); //send request

		Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

		Log.e("TMEXECUTION EXECUTION", response.toString());

	}
	
}
