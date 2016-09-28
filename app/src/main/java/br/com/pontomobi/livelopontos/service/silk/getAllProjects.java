package br.com.pontomobi.livelopontos.service.silk;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.xml.transform.stream.StreamSource;

import br.com.pontomobi.livelopontos.util.ConstantsServices;
import br.com.pontomobi.livelopontos.util.LerArquivo;
import br.com.pontomobi.livelopontos.util.SoapHelper;
import br.com.pontomobi.livelopontos.util.Utils;
import br.com.pontomobi.livelopontos.util.ViewConstants;

public class getAllProjects {

	public int executar(long sessionid, String projectName) throws Exception{

		SoapObject request = new SoapObject("http://10.140.88.15:19120/axislegacy/sccentities",
				"getAllProjects");
		request.addProperty("sessionId", sessionid);
		request.addProperty("projectName", projectName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);  //prepare request
		AndroidHttpTransport httpTransport = new AndroidHttpTransport("http://10.140.88.15:19120/axislegacy/sccentities?wsdl");
		httpTransport.debug = true;

		httpTransport.call("", envelope); //send request

		Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();

		Log.e("PROJECTID", response.get(0).getProperty("id").toString());

		return Integer.valueOf(response.get(0).getProperty("id").toString());
	}

}
