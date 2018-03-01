package es.bde.aps.jbs.workitem.test.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class ConfigurationAbstract implements IConfiguration {

	private SAXParser parser;
	protected HandlerAbstract handler;

	/**
	 * 
	 * @throws Exception
	 */
	protected void load(String fileName) throws Exception {
		// TODO Ap�ndice de m�todo generado autom�ticamente

		InputStream input = ConfigurationTestPlSql.class.getResourceAsStream(fileName);
		if (input == null)
			throw new Exception("No se encuentra el fichero [" + fileName + "] en el classpath");

		handler = createHandler();
		parse(input, handler);
	}

	/**
	 * 
	 * @return
	 */
	abstract HandlerAbstract createHandler();

	/**
	 * 
	 * @param input
	 * @param handler
	 */
	private void parse(InputStream input, DefaultHandler handler) {
		try {
			getParser().parse(input, handler);
		} catch (SAXException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return
	 */
	private SAXParser getParser() {
		try {
			if (parser == null) {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				parser = factory.newSAXParser();
			}

		} catch (Exception saxe) {
			saxe.printStackTrace();
		}
		return parser;
	}
}
