import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;

public class CustomGLCanvas extends GLCanvas{
	
	private static final long serialVersionUID = -8270092477034459327L;
	
	private GLCapabilities capabilities;
	
	public CustomGLCanvas() {
		super();
	}
	
	public CustomGLCanvas(GLCapabilities capabilities) {
		super(capabilities);
		this.capabilities = capabilities;
	}
	
}
