import javax.swing.*;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

public class CustomFrame extends JFrame{
	
	private static final long serialVersionUID = -5046438866253734116L;
	
	GLProfile profile;
	GLCapabilities capabilities;
	
	Renderer renderer;
	CustomGLCanvas canvas;
	Animator animator;
	
	Timer timer;
	
	public CustomFrame(int width, int heigth) {	
		this.initOpenGL();  // Initialize OpenGL
		
		// Set properties for frame
		this.setSize(width, heigth);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.initObjects(); // Initialize all objects and set their properties for this canvas (canvases, buttons, layouts etc)	
		this.addObjects();  // Add initialized objects to this frame
		this.startRenderingCanvas(); // Start animator for canvas
	}
	
	private void initOpenGL() {
		profile = GLProfile.getDefault();
		capabilities = new GLCapabilities(profile);
	}
		
	private void initObjects() {
		renderer = new Renderer();
		canvas = new CustomGLCanvas(capabilities);
		animator = new Animator(canvas);
		animator.setUpdateFPSFrames(1, null);
	}
	
	private void addObjects() {
		canvas.addGLEventListener(renderer);
		this.add(canvas);
	}
	
	private void startRenderingCanvas() {
		SwingUtilities.invokeLater(() -> {
			animator.start();
		});
	}
}
