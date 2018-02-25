package es.bde.aps.jbs.eaijava;

public class EAIConstants {

	public static final String SEED = "1234567890123456";
	public static final String PARAM_PROCEDURE = "Procedure";
	public static final String PARAM_LIST_IN = "Input Fields";
	public static final String PARAM_LIST_OUT = "Output Fields";
	public static final String PROP_JBOSS_CONFIG_DIR = "jboss.server.config.dir";

	public static final String PROPERTIES_DATABASE = "database.properties";
	public static final String PROPERTIES_MAIL = "mail.properties";

	// Clave que identifica el driver JDBC
	public static final String DRIVER_NAME = ".db_driver";
	// Clave que identifica la cadena de conexi�n JDBC
	public static final String CONNECTION = ".db_connection";
	// Clave que identifica el usurio de la base de datos
	public static final String USER = ".db_user";
	// Clave que identifica la password encriptada del usuario de base de datos
	public static final String PASSWORD = ".db_password";
	// Clave que identifica el propietario del esquema de la base de datos
	public static final String SCHEMA_OWNER = ".db_schemaowner";
	// Clave que identifica si la conexiones a la base de datos son autocommit.
	public static final String AUTO_COMMIT = ".db_autocommit";
	// Clave que identifica el n�mero de conexiones activas en el pool a la
	// base de datos
	public static final String MAX_ACTIVE = ".db_max_active";
	// Clave que identifica el n�mero de conexiones ocupadas en el pool a la
	// base de datos
	public static final String MAX_IDLE = ".db_max_idle";
	// Nombre del driver de Oracle
	public static final String DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";

	public static final String MAIL_FROM = "From";
	public static final String MAIL_TO = "To";
	public static final String MAIL_TO_GROUP = "To group";
	public static final String MAIL_CC = "Cc";
	public static final String MAIL_CCO = "Cco";
	public static final String MAIL_SUBJECT = "Subject";
	public static final String MAIL_BODY = "Body";
	public static final String MAIL_ATTACH_NAME = "MAIL_ATT_NAME";
	public static final String MAIL_ATTACH_FILE = "MAIL_ATT_FILE";

	public static final String PROP_DIRECTORY_ATTACH = "eaijava.directory.attach";
	public static final String PROP_SERVER_MAIL = "mail.smtp.host";
	public static final String PROP_SMTP_USER = "mail.smtp.user";
	public static final String PROP_SMTP_PWD = "mail.smtp.password";
	public static final String PROP_PROTOCOL_MAIL = "eaijava.protocol";
	public static final String PROP_SQL_USERS_GROUP = "eaijava.sql.users.group";

	public static final String MAIL_DOMAIN_OLD = "@outlook";
	public static final String MAIL_DOMAIN_NEW = "@correo.interno";

	public static final String SEPARATOR = ";";

}
