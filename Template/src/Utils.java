import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;

public class Utils {
	// Shader management part
	public static int createShaderProgram(String vShaderSourcePath, String fShaderSourcePath) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] vertCompiled = new int[1];
		int[] fragCompiled = new int[1];
		int[] linked = new int[1];
		
		String vShaderSource[] = readShaderSource(vShaderSourcePath);
		String fShaderSource[] = readShaderSource(fShaderSourcePath);
		
		int vShader = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
		gl.glShaderSource(vShader, vShaderSource.length, vShaderSource, null, 0);
		gl.glCompileShader(vShader);
		
		checkOpenGLError();
		gl.glGetShaderiv(vShader, GL4.GL_COMPILE_STATUS, vertCompiled, 0);
		if(vertCompiled[0] != 1) {
			System.out.println("Vertex compilation failed!!!");
			printShaderLog(vShader);
		}
		
		int fShader = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);
		gl.glShaderSource(fShader, fShaderSource.length, fShaderSource, null, 0);
		gl.glCompileShader(fShader);
		
		checkOpenGLError();
		gl.glGetShaderiv(fShader, GL4.GL_COMPILE_STATUS, fragCompiled, 0);
		if(fragCompiled[0] != 1) {
			System.out.println("Fragment compilation failed!!!");
			printShaderLog(fShader);
		}
		
		if ((vertCompiled[0] != 1) || (fragCompiled[0] != 1)){ 
			System.out.println("\nCompilation error; return-flags:");
			System.out.println(" vertCompiled = " + vertCompiled[0] + " ; fragCompiled = " + fragCompiled[0]);
		}
		
		int vfProgram = gl.glCreateProgram();
		gl.glAttachShader(vfProgram, vShader);
		gl.glAttachShader(vfProgram, fShader);
		gl.glLinkProgram(vfProgram);
		
		checkOpenGLError();
		gl.glGetProgramiv(vfProgram, GL4.GL_LINK_STATUS, linked, 0);
		if(linked[0] != 1) {
			System.out.println("Program linking failed!!!");
			printProgramLog(vfProgram);
		}
		
		gl.glDeleteShader(vShader);
		gl.glDeleteShader(fShader);
		
		return vfProgram;
	}
	// End for shader management part
	
	// Read shader from file
	public static String[] readShaderSource(String pathToFile) {
		ArrayList<String> shaderSourceAsList = new ArrayList<String>();
		String shaderSource[];
		Scanner sc;
		
		try {
			sc = new Scanner(new File(pathToFile));
			while(sc.hasNext()) {
				shaderSourceAsList.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		shaderSource = new String[shaderSourceAsList.size()]; 
		
		for (int i = 0; i < shaderSourceAsList.size(); i++) {
			shaderSource[i] = shaderSourceAsList.get(i) + "\n";
		}
		
		return shaderSource;
	}
	
	// Check for errors part
	public static void printShaderLog(int shader) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;
		
		// determine the length of the shader compilation log
		gl.glGetShaderiv(shader, GL4.GL_INFO_LOG_LENGTH, len, 0);
		
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(shader, len[0], chWrittn, 0, log, 0);
			System.out.println("Shader Info Log:");
			for(int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}
	
	public static void printProgramLog(int prog) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;
		
		// determine the length of the shader compilation log
		gl.glGetProgramiv(prog, GL4.GL_INFO_LOG_LENGTH, len, 0);
		
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(prog, len[0], chWrittn, 0, log, 0);
			System.out.println("Program Info Log:");
			for(int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}
	
	public static boolean checkOpenGLError() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		boolean foundError = false;
		GLU glu = new GLU();
		int glErr = gl.glGetError();
		while(glErr != GL4.GL_NO_ERROR) {
			System.err.println("glError:" + glu.gluErrorString(glErr));
			foundError = true;
			glErr = gl.glGetError();
		}
		return foundError;
	}
	// End check for errors part
}
