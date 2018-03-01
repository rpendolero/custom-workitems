package es.bde.aps.jbs.workitem.test.config;

public enum ConfigType {

	//
	CONFIG_PL_SQL("configTestPlSql"),
	//
	CONFIG_MAIL("configTestMail"),
	//
	CONFIG_START_CASE("configTestStartCase");

	private String fileName;

	private ConfigType(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

}
