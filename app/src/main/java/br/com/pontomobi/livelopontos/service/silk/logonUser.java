package br.com.pontomobi.livelopontos.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

public class logonUser {

	public long executar() throws Exception{

		SoapObject request = new SoapObject("http://10.140.88.15:19120/services/sccsystem",
				"logonUser");
		request.addProperty("username", "admin");
		request.addProperty("plainPwd", "admin");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);  //prepare request
		AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://10.140.88.15:19120/services/sccsystem?wsdl");
		httpTransport.debug = true;

		httpTransport.call("", envelope); //send request

		Log.e("SESSIONID", envelope.getResponse().toString());

		return Long.parseLong(envelope.getResponse().toString());

	}
	
}
