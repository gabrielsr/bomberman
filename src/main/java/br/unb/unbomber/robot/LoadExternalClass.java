/*******************************************************************************
 * [Restricted Materials of IBM] - Use restricted, please refer to the "SOURCE
 * COMPONENTS AND SAMPLE MATERIALS" and the "PROHIBITED USES" terms and
 * conditions in the IBM International License Agreement for non warranted IBM
 * software (ILA).
 * 
 * Code Rally
 * 
 * (R) Copyright IBM Corporation 2012.
 * 
 * U.S. Government Users Restricted Rights:  Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp. 
 * 
 * From the ILA for non warranted IBM software:
 * 
 * SOURCE COMPONENTS AND SAMPLE MATERIALS
 * 
 * The Program may include some components in source code form ("Source
 * Components") and other materials identified as Sample Materials. Licensee
 * may copy and modify Source Components and Sample Materials for internal use
 * only provided such use is within the limits of the license rights under this
 * Agreement, provided however that Licensee may not alter or delete any
 * copyright information or notices contained in the Source Components or Sample
 * Materials. IBM provides the Source Components and Sample Materials without
 * obligation of support and "AS IS", WITH NO WARRANTY OF ANY KIND, EITHER
 * EXPRESS OR IMPLIED, INCLUDING THE WARRANTY OF TITLE, NON-INFRINGEMENT OR
 * NON-INTERFERENCE AND THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * PROHIBITED USES
 * 
 * Licensee may not use or authorize others to use the Program or any part of
 * the Program, alone or in combination with other products, in support of any
 * of the following High Risk Activities: design, construction, control, or
 * maintenance of nuclear facilities, mass transit systems, air traffic control
 * systems, weapons systems, or aircraft navigation or communications, or any
 * other activity where program failure could give rise to a material threat of
 * death or serious personal injury.
 ******************************************************************************/
package br.unb.unbomber.robot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class LoadExternalClass<E> {
	
	public static final String copyrightStatement =
			"[Restricted Materials of IBM] - Use restricted, please refer to the \"SOURCE\n"	+
			"COMPONENTS AND SAMPLE MATERIALS\" and the \"PROHIBITED USES\" terms and\n"			+
			"conditions in the IBM International License Agreement for non warranted IBM\n"		+
			"software (ILA).\n"																	+
			"\n"																				+
			"Code Rally\n"																		+
			"\n"																				+
			"(R) Copyright IBM Corporation 2012.\n"												+
			"\n"																				+
			"U.S. Government Users Restricted Rights:  Use, duplication or disclosure"			+
			"restricted by GSA ADP Schedule Contract with IBM Corp."							+
			"\n"																				+
			"From the ILA for non warranted IBM software:\n"									+
			"\n"																				+
			"SOURCE COMPONENTS AND SAMPLE MATERIALS\n"											+
			"\n"																				+
			"The Program may include some components in source code form (\"Source\n"			+
			"Components\") and other materials identified as Sample Materials. Licensee\n"		+
			"may copy and modify Source Components and Sample Materials for internal use\n"		+
			"only provided such use is within the limits of the license rights under this\n"	+
			"Agreement, provided however that Licensee may not alter or delete any\n"			+
			"copyright information or notices contained in the Source Components or Sample\n"	+
			"Materials. IBM provides the Source Components and Sample Materials without\n"		+
			"obligation of support and \"AS IS\", WITH NO WARRANTY OF ANY KIND, EITHER\n"		+
			"EXPRESS OR IMPLIED, INCLUDING THE WARRANTY OF TITLE, NON-INFRINGEMENT OR\n"		+
			"NON-INTERFERENCE AND THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY\n"	+
			"AND FITNESS FOR A PARTICULAR PURPOSE.\n"											+
			"\n"																				+
			"PROHIBITED USES\n"																	+
			"\n"																				+
			"Licensee may not use or authorize others to use the Program or any part of\n"		+
			"the Program, alone or in combination with other products, in support of any\n"		+
			"of the following High Risk Activities: design, construction, control, or\n"		+
			"maintenance of nuclear facilities, mass transit systems, air traffic control\n"	+
			"systems, weapons systems, or aircraft navigation or communications, or any\n"		+
			"other activity where program failure could give rise to a material threat of\n"	+
			"death or serious personal injury.\n";
	
	private String packageName = "";
	private String className;
	private String sourceCode;
	private Iterable<? extends File> classpath;
	private Iterable<String> options;
	private ClassLoader parent;
	
	private static final Logger log = Logger.getLogger("coderally");

	public LoadExternalClass() {
		this(null);
	}
	
	public LoadExternalClass(ClassLoader parent) {
		this.parent = parent;
	}

	public LoadExternalClass<E> setClassName(String name) {
		this.className = name;
		return this;
	}

	public LoadExternalClass<E> setSource(String sourceCode) {
	//Can only compile default-level classes
		this.sourceCode = sourceCode.replaceFirst("public class", "class");
		Scanner scan = new Scanner(sourceCode);
		while(scan.hasNext()) {
			String line = scan.nextLine().trim();
			if (line.startsWith("package")) {
				packageName = line.split(" ")[1];
				int semicolon = packageName.indexOf(";");
				//Strip off ';' at end
				if (semicolon > -1) {
					packageName = packageName.substring(0, semicolon);
				}
				break;
			}
		}
		scan.close();
		return this;
	}

	public LoadExternalClass<E> setOptions(Iterable<String> options) {
		this.options = options;
		return this;
	}
	
	public LoadExternalClass<E> setClasspath(Iterable<? extends File> cp) {
		this.classpath = cp;
		return this;
	}

	private String getFullClassName() {
		if (packageName.length() > 0) {
			return packageName + "." + className;
		} else {
			return className;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public CompilationResult<E> compile() throws ClassNotFoundException, IllegalArgumentException {
		if (className == null || sourceCode == null) {
			throw new IllegalStateException("Class name and source code may not be null");
		}
		
		E robot = null;
		List<Diagnostic<? extends JavaFileObject>> errors = Collections.emptyList();

		//Create the in-memory file for the source code
		BufferedJavaSource source = new BufferedJavaSource(getFullClassName(), sourceCode);
		//Get the instance of the compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		if(compiler == null)
		    throw new RuntimeException("Couldn't access system java compiler. Is tools.jar in classpath?");
		
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
		try {
			stdFileManager.setLocation(StandardLocation.CLASS_PATH, classpath);
		} catch (IOException e) {
			log.log(Level.WARNING, "Unable to set classpath");
		}
		DynamicFileManager fileManager = new DynamicFileManager(stdFileManager, parent);

		//Create a list of the class we want to compile
		ArrayList<JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>(1);
		compilationUnits.add(source);
		
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter err = new PrintWriter(baos, true);
		CompilationTask task = compiler.getTask(err, fileManager, diagnostics, options, null, compilationUnits);
		if (task.call()) {
			//Fetches the newly compiled class from the classloader
			Class<? extends Robot> clazz = (Class<? extends Robot>) fileManager.getClassLoader(null).loadClass(getFullClassName());
			
			try {
				//Invokes the empty constructor
				Constructor<?> constructor = clazz.getDeclaredConstructor((Class[])null);
				constructor.setAccessible(true);
				robot = (E) constructor.newInstance((Object[])null);
				return new CompilationResult<E>(robot, errors, "");
			} catch (SecurityException e) {
				throw new RuntimeException("Constructor for " + className + " could not be accessed", e);
			} catch (InstantiationException e) {
				throw new RuntimeException(className + " could not be instantiated", e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Constructor for " + className + " could not be accessed", e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(className + " could not be instantiated", e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(className + " was missing an empty constructor", e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(className + " could not be instantiated", e);
			}
		} else {
			return new CompilationResult<E>(robot, diagnostics.getDiagnostics(), new String(baos.toByteArray()));
		}
	}

	/**
	 * The dynamic file manager overrides the standard file manager to allow
	 * it to search in-memory class files instead of looking at the file system
	 * for compiled java bytecode
	 */
	private static class DynamicFileManager extends ForwardingJavaFileManager<JavaFileManager> {
		private final DynamicClassLoader classLoader;
		private Map<String, CompiledJavaSource> fileObjects = new HashMap<String, CompiledJavaSource>();
		protected DynamicFileManager(JavaFileManager fileManager, ClassLoader parent) {
			super(fileManager);
			classLoader = new DynamicClassLoader(parent);
		}

		@Override
		public ClassLoader getClassLoader(Location location) {
			return classLoader;
		}

		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
			final String key = className + kind.extension;
			CompiledJavaSource source = fileObjects.get(key);
			if (source == null) {
				source = new CompiledJavaSource(className, kind);
				fileObjects.put(key, source);
			}
			return source;
		}

		/**
		 * Classloader specifically written to load any in-memory compiled java code
		 */
		private class DynamicClassLoader extends SecureClassLoader {
			
			protected DynamicClassLoader(ClassLoader parent) {
				super(parent);
			}
			
			@Override
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				CompiledJavaSource compiled = fileObjects.get(name + Kind.CLASS.extension);
				if (compiled != null) {
					byte[] bytes = compiled.getBytes();
					return super.defineClass(name, bytes, 0, bytes.length);
				} else {
					return super.findClass(name);
				}
			}
		}
	}

	/**
	 * Represents an in-memory compiled java class, which allows the classloader
	 * to retrieve the raw bytes instead of reading a .class file on the filesystem
	 */
	private static class CompiledJavaSource extends SimpleJavaFileObject {
		private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		protected CompiledJavaSource(String className, Kind kind) {
			super(URI.create("string:///" + className.replaceAll("//.", "/")  + kind.extension), kind);
		}

		public byte[] getBytes() {
			return bos.toByteArray();
		}

		@Override
		public OutputStream openOutputStream() throws IOException {
			return bos;
		}
	}

	/**
	 * Represents an in-memory java source code file, which allows the compiler
	 * to compile the source code without needing a file on the filesystem
	 */
	private static class BufferedJavaSource extends SimpleJavaFileObject {
		private final String content;
		protected BufferedJavaSource(String className, String content){
			super(URI.create("string:///" + className.replaceAll("//.", "/")  + Kind.SOURCE.extension), Kind.SOURCE);
			this.content = content;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreErrors) {
			return content;
		}

	}
	
	public static class CompilationResult<E> {
		private final E robot;
		private final List<Diagnostic<?>> errors;
		private final String message;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private CompilationResult(E robot, List<Diagnostic<? extends JavaFileObject>> list, String message) {
			this.robot = robot;
			this.errors =  (List<Diagnostic<?>>)(List)list;
			this.message = message;
		}

		public E getRobot() {
			return robot;
		}

		public List<Diagnostic<?>> getErrors() {
			return errors;
		}

		public String getMessage() {
			return message;
		}
	}
}
