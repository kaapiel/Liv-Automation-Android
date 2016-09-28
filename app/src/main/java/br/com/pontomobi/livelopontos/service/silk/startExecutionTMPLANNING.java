package br.com.pontomobi.livelopontos.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.util.Vector;

public class startExecutionTMPLANNING {

	public long executar(long sessionid, int executionDefId, String version, String build, String host, String port) throws Exception{

		SoapObject request = new SoapObject("http://10.140.88.15:19120/Services1.0/services/tmplanning",
				"startExecution");

		request.addProperty("sessionId", sessionid);
		request.addProperty("executionDefId", executionDefId);
		request.addProperty("version", version);
		request.addProperty("build", build);
		request.addProperty("execServerHostName", host);
		request.addProperty("execServerPort", port);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);  //prepare request
		AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://10.140.88.15:19120/Services1.0/services/tmplanning?wsdl");
		httpTransport.debug = true;

		httpTransport.call("", envelope); //send request

		Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

		Log.e("TMPLANNING EXECUTION", response.get(0).toString());

		return 0;

	}
	
}
