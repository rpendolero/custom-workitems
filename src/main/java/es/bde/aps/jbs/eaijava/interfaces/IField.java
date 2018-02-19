package es.bde.aps.jbs.eaijava.interfaces;

public interface IField {
	public static final char STRING = 'A';
	public static final char INTEGER = 'N';
	public static final char DOUBLE = 'R';
	public static final char DATE = 'D';
	public static final char TIME = 'T';
	public static final char DATETIME = 'O';

	/**
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return
	 */
	public abstract Object getValue();

	/**
	 * @return
	 */
	public abstract char getType();

}
