package es.bde.aps.jbs.workitem.util;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class ClassSearchUtils {

	private static final Logger log = LoggerFactory.getLogger(ClassSearchUtils.class.getName());

	/**
	 * Classloader to be used to obtain resources from file system.
	 */
	private ClassLoader classloader;

	/**
	 * List of the resource found in the classpath.
	 */
	private ArrayList list;

	/**
	 * Extension of the resource to be found in the classpath.
	 */
	private String extension;

	private String prefix;

	/**
	 * Search for the resource with the extension in the classpath. Method
	 * self-instantiate factory for every call to ensure thread safety.
	 * 
	 * @param extension
	 *            Mandatory extension of the resource. If all resources are required
	 *            extension should be empty string. Null extension is not allowed
	 *            and will cause method to fail.
	 * @return List of all resources with specified extension.
	 */
	@SuppressWarnings("unchecked")
	public static List<Class<?>> searchClassPath(String prefix) {
		return searchClassPath(prefix, ".class");
	}

	/**
	 * Search for the resource with the extension in the classpath. Method
	 * self-instantiate factory for every call to ensure thread safety.
	 * 
	 * @param extension
	 *            Mandatory extension of the resource. If all resources are required
	 *            extension should be empty string. Null extension is not allowed
	 *            and will cause method to fail.
	 * @return List of all resources with specified extension.
	 */
	public static List searchClassPath(String prefix, String extension) {
		System.out.println("searchClassPath [" + prefix + "] [" + extension + "]");
		ClassSearchUtils factory = new ClassSearchUtils();
		factory.prefix = prefix;
		return factory.find(extension);
	}

	/**
	 * Search for the resource with the extension in the classpath.
	 * 
	 * @param extension
	 *            Mandatory extension of the resource. If all resources are required
	 *            extension should be empty string. Null extension is not allowed
	 *            and will cause method to fail.
	 * @return List of all resources with specified extension.
	 */
	@SuppressWarnings("unchecked")
	private List<Class<?>> find(String extension) {
		this.extension = extension;
		this.list = new ArrayList();
		this.classloader = this.getClass().getClassLoader();
		String classpath = System.getProperty("java.class.path");

		try {
			Method method = this.classloader.getClass().getMethod("getClassPath", (Class<?>) null);
			if (method != null) {
				classpath = (String) method.invoke(this.classloader, (Object) null);
			}
		} catch (Exception e) {
			// ignore
		}
		if (classpath == null) {
			classpath = System.getProperty("java.class.path");
		}

		StringTokenizer tokenizer = new StringTokenizer(classpath, File.pathSeparator);
		String token;
		File dir;
		String name;
		while (tokenizer.hasMoreTokens()) {

			token = tokenizer.nextToken();
			System.out.println("Searching [" + token + "]");
			dir = new File(token);
			if (dir.isDirectory()) {
				lookInDirectory("", dir);
			}
			if (dir.isFile()) {
				name = dir.getName().toLowerCase();
				if (name.endsWith(".zip") || name.endsWith(".jar")) {
					this.lookInArchive(dir);
				}
			}
		}
		return this.list;
	}

	/**
	 * @param name
	 *            Name of to parent directories in java class notation (dot
	 *            separator)
	 * @param dir
	 *            Directory to be searched for classes.
	 */
	@SuppressWarnings("unchecked")
	private void lookInDirectory(String name, File dir) {
		log.debug("Looking in directory [" + dir.getName() + "].");
		File[] files = dir.listFiles();
		File file;
		String fileName;
		final int size = files.length;
		for (int i = 0; i < size; i++) {
			file = files[i];
			fileName = file.getName();
			if (file.isFile() && fileName.toLowerCase().endsWith(this.extension)) {
				try {
					if (this.extension.equalsIgnoreCase(".class")) {
						fileName = fileName.substring(0, fileName.length() - 6);
						// filter ignored resources
						if (!(name + fileName).startsWith(this.prefix)) {
							continue;
						}

						System.out.println("Found class: [" + name + fileName + "].");
						this.list.add(Class.forName(name + fileName));
					} else {
						this.list.add(this.classloader.getResource(name.replace('.', File.separatorChar) + fileName));
					}
				} catch (ClassNotFoundException e) {
					// ignore
				} catch (NoClassDefFoundError e) {
					// ignore too
				} catch (ExceptionInInitializerError e) {
					if (e.getCause() instanceof HeadlessException) {
						// running in headless env ... ignore
					} else {
						throw e;
					}
				}
			}
			// search recursively.
			// I don't like that but we will see how it will work.
			if (file.isDirectory()) {
				lookInDirectory(name + fileName + ".", file);
			}
		}

	}

	/**
	 * Search archive files for required resource.
	 * 
	 * @param archive
	 *            Jar or zip to be searched for classes or other resources.
	 */
	@SuppressWarnings("unchecked")
	private void lookInArchive(File archive) {
		System.out.println("Looking in archive [" + archive.getName() + "] for extension [" + this.extension + "].");
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(archive);
		} catch (IOException e) {
			System.out.println("Non fatal error. Unable to read jar item.");
			return;
		}
		Enumeration entries = jarFile.entries();
		JarEntry entry;
		String entryName;
		while (entries.hasMoreElements()) {
			entry = (JarEntry) entries.nextElement();
			entryName = entry.getName();
			if (entryName.toLowerCase().endsWith(this.extension)) {
				try {
					if (this.extension.equalsIgnoreCase(".class")) {
						// convert name into java classloader notation
						entryName = entryName.substring(0, entryName.length() - 6);
						entryName = entryName.replace('/', '.');

						// filter ignored resources
						if (!entryName.startsWith(this.prefix)) {
							continue;
						}

						System.out.println("Found class: [" + entryName + "]. ");
						this.list.add(Class.forName(entryName));
					} else {
						this.list.add(this.classloader.getResource(entryName));
						System.out.println("Found appropriate resource with name [" + entryName + "]. Resource instance:" + this.classloader.getResource(entryName));
					}
				} catch (Throwable e) {
					// ignore
					System.out.println("Unable to load resource [" + entryName + "] form file [" + archive.getAbsolutePath() + "].");
				}
			}
		}
	}

}
