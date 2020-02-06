package br.com.aguido.livautomation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Gabriel Fraga
 * 
 * Essa classe tem como finalidade ser uma classe helper,
 * contendo vários métodos que podem ser usados por diversas classes de forma genérica
 * 
 * 
 */
public class Utils {

	/**
	 * @author Gabriel Fraga
	 * 
	 * Método responsável por carregar o arquivo xptah com todos os idenficadores da página
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Properties carregarIdentificadores() throws FileNotFoundException, IOException, URISyntaxException {
		Properties ids = new Properties();

		if(!Inet4Address.getLocalHost().getHostName().toUpperCase().contains("VININM701LIV")){ //maquina local
			File file = new File(Constants.xpathProperties);
			ids.load(new FileInputStream(file));
		} else {
			File file = new File(ViewConstants.Properties.CONFIG_PATH);
			ids.load(new FileInputStream(file));
		}

		return ids;
	}


	/**
	 * @author Gabriel Fraga
	 * 
	 * Método responsável por carregar os linls de configuração dentro do arquivo properties
	 * 
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Properties carregarLinks() throws FileNotFoundException, IOException, URISyntaxException {
		Properties ids = new Properties();

		if(!Inet4Address.getLocalHost().getHostName().toUpperCase().contains("VININM701LIV")){ //máquina local
			File file = new File(Constants.configProperties);
			ids.load(new FileInputStream(file));
		} else {
			File file = new File(ViewConstants.Properties.CONFIG_LINKS);
			ids.load(new FileInputStream(file));
		}

		return ids;
	}

	public static Properties carregarUrlsServicos() throws FileNotFoundException, IOException, URISyntaxException {

		Properties ids = new Properties();

		if(!Inet4Address.getLocalHost().getHostName().toUpperCase().contains("VININM701LIV")){ //máquina local
			File file = new File(Constants.servicosProperties);
			ids.load(new FileInputStream(file));
		} else {
			File file = new File(ViewConstants.Properties.CONFIG_SERVICOS);
			ids.load(new FileInputStream(file));
		}

		return ids;
	}

	public static List<String> lerArquivoTxt(File file) throws IOException {

		FileReader fileReader = new FileReader(file);
		BufferedReader reader = new BufferedReader(fileReader);
		String data = null;
		List<String> listSku = new ArrayList<String>();

		try {
			while ((data = reader.readLine()) != null) {
				listSku.add(data);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		fileReader.close();
		reader.close();

		return listSku;
	}

	public static String getTagValue(String xml, String tag) throws Exception { 

		try {

			String closeTag = new StringBuffer(tag).insert(1, "/").toString(); // fecha a tag adicionando "/"  

			if(closeTag.contains("logonUserReturn")){
				int from = xml.indexOf(tag) + tag.length();  
				int to = xml.indexOf("</logonUserReturn>");  
				return xml.substring(from, to);
			} else if(closeTag.contains("id xsi:type=\"xsd:int\"")) {
				int from = xml.indexOf(tag) + tag.length();  
				int to = xml.indexOf("</id>");  
				return xml.substring(from, to);
			} else {
				int from = xml.indexOf(tag) + tag.length();  
				int to = xml.indexOf(closeTag);  
				return xml.substring(from, to);
			}
		} 
		catch (Exception e) {

			throw new Exception("Erro ao obter o valor da Tag: ", e);
		}  
	}

}