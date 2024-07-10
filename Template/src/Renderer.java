import com.jogamp.opengl.GL4;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

public class Renderer implements GLEventListener{
	
	private int program;

	@Override
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		program = Utils.createShaderProgram(".\\shaders\\vertex.glsl", ".\\shaders\\fragment.glsl");
		
		gl.glClearColor(0, 1, 0, 1);
		gl.glClear(GL_COLOR_BUFFER_BIT);
	}


	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		
		gl.glUseProgram(program);
		Utils.checkOpenGLError();
		gl.glDrawArrays(GL_TRIANGLES, 0, 3);
		Utils.checkOpenGLError();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
