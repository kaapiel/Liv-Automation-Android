package br.com.aguido.livautomation.util;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.SOAPConnection;
//import javax.xml.soap.SOAPConnectionFactory;
//import javax.xml.soap.SOAPMessage;
//import javax.xml.soap.SOAPPart;

public class SoapHelper implements Serializable{

	/**
	 * @author Marllon de Souza
	 * 
	 * Classe responsável por auxiliar na chamadas SOAP de serviço
	 * 
	 */
	private static final long serialVersionUID = 5420470064697806258L;

	/**
	 * @author Marllon de Souza
	 * 
	 * Método responsável por formatar o xml para o formato identado
	 * 
	 * @param unformattedXml
	 * @return
	 * @throws Exception 
	 */
	public static String format(String unformattedXml) throws Exception {

		try {

//			Document document = parseXmlFile(unformattedXml);
//
//			OutputFormat format = new OutputFormat(document);
//			format.setLineWidth(80);
//			format.setIndenting(true);
//			format.setIndent(2);
//			format.setOmitDocumentType(true);
//
//			Writer out = new StringWriter();
//
//			XMLSerializer serializer = new XMLSerializer(out, format);
//
//			serializer.serialize(document);
//
//			return out.toString();

			return null;
		} 
		catch (Exception e) {

			throw new Exception("Erro ao formatar o arquivo: ", e);
		}
	}
	
	/**
	 * @author Marllon de Souza
	 * 
	 * Método responsável por converter a string em xml
	 * 
	 * @param in
	 * @return
	 * @throws Exception 
	 */
	private static Document parseXmlFile(String in) throws Exception {

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));

			return db.parse(is);
		}  
		catch (Exception e) {

			throw new Exception("Erro ao realizar parse do arquivo: ", e);
		}
	}
	
	/**
	 * @author Marllon de Souza
	 * 
	 * Método responsável por criar um arquivo no diretório específico. 
	 * 
	 * @param file
	 * @param content
	 */
	public static void writeFile(File file, String content) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fos));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * @author Marllon de Souza
	 * 
	 * Método responsável por executar a transação SOAP passando como paramétro a request e o endpoint 
	 * 
	 * @param streamSource
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	public static String executarTransacao(StreamSource streamSource, String endpoint, boolean headerSoap, String valeuHeaderSaop, String className) throws Exception {

//		SOAPConnection conn = null;
//
		try {
//
//			URL url = new URL(endpoint);
//
//			// Create the connection
//			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
//
//			conn = scf.createConnection();
//
//			// Create message
//			MessageFactory mf = MessageFactory.newInstance();
//
//			SOAPMessage msg = mf.createMessage();
//			msg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
//			msg.getMimeHeaders().setHeader("Content-Type", "text/xml;charset=UTF-8");
//
//			if(headerSoap == true){
//
//				msg.getMimeHeaders().setHeader("SOAPAction", valeuHeaderSaop+className);
//			}else{
//				msg.getMimeHeaders().setHeader("SOAPAction", "");
//
//			}
//
//			// Object for message parts
//			SOAPPart sp = msg.getSOAPPart();
//			StreamSource prepMsg = streamSource;
//
//			sp.setContent(prepMsg);
//
//			// Save message
//			msg.saveChanges();
//
//			Thread.sleep(75);
//
//			// Send
//			SOAPMessage rp = conn.call(msg, url);
//
//			Thread.sleep(125);
//
//			// Get reply content
//			Source sc = rp.getSOAPPart().getContent();
//
//			StringWriter writer = new StringWriter();
//	        StreamResult result = new StreamResult(writer);
//	        TransformerFactory tFactory = TransformerFactory.newInstance();
//	        Transformer transformer = tFactory.newTransformer();
//
//	        /*
//	         * Configure transformer to produce:
//	         * - Indented output.
//	         * - A standalone document.
//	         */
//
//	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//	        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
//	        transformer.transform(sc, result);
//
//	        // Grava arquivo com resultado do processamento
//			String resultFormatted = SoapHelper.format(writer.toString());
//
//			return resultFormatted;

		return null;
		} 
		catch (Exception e) {
			
			throw new Exception("Erro ao executar o serviço: ", e);
		}
		finally {
			
			//conn.close();
		}	
	}	

	/**
	 * Inmetrics<BR>
	 * 
	 * @since 16 de nov de 2015 15:29:05
	 * @author Marllon Souza<BR>
	 * @throws Exception 
	 */
	public static String mergeTemplate(String template, Map<String, String> values) throws Exception {
		
		try {
		
			for (String key : values.keySet()) {
			
				String value = values.get(key);
				
				template = template.replace(key, value);
			}
			
			return template;
		} 
		catch (Exception e) {
		
			throw new Exception("Erro ao realizar o merge do template: ", e);
		}
	}
	
	/**
	 * Inmetrics<BR>
	 * 
	 * @since 16 de nov de 2015 15:29:05
	 * @author Marllon Souza<BR>
	 * @throws Exception 
	 */
	public static String getTagValue(String xml, String tag) throws Exception { 
	   
		try {
			
			String closeTag = new StringBuffer(tag).insert(1, "/").toString(); // fecha a tag adicionando "/"  
			int from = xml.indexOf(tag) + tag.length();  
			int to = xml.indexOf(closeTag);  
  
			return xml.substring(from, to);
		} 
		catch (Exception e) {
			
			throw new Exception("Erro ao obter o valor da Tag: ", e);
		}  
	}		
}