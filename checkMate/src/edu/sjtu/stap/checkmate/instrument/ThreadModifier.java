package edu.sjtu.stap.checkmate.instrument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.jacontebe.asm.MethodInfor;

/**
 * Instrument java.lang.Thread class and store instrumented class into
 * <i>boot_classes</i> directory. Specify which methods to instrument in
 * {@link #addMethods2Transform(List)}
 * 
 * @author Ziyi
 *
 */
public class ThreadModifier extends ClassModifier{
	
	public static void main(String[] args) throws IOException {
		ThreadModifier modifier=new ThreadModifier();
		modifier.modifyClass();
	}

	/**
	 * Store instrumented classes into the default output directory: [project_home]/boot_classes/
	 */
	public ThreadModifier(){
		this.bootPath=getDefaultBootPath();
		initMvFactory();
	}
	
	/**
	 * 
	 * @param bootPath path where instrumented classes are located. End with "/". 
	 */
	public ThreadModifier(String bootPath){
		this.bootPath=bootPath;
		initMvFactory();
	}
	
	private void initMvFactory(){
		mvfactory=new ThreadMVFactory();
	}
	
	protected Map<String, Object> specifyConfig() {
		return basicConfig("java.lang.Thread");
	}

	protected List<MethodInfor> addMethods2Transform() {
		List<MethodInfor> methods2Tran=new ArrayList<MethodInfor>();
		methods2Tran.add(new MethodInfor("start", "()v"));
		methods2Tran.add(new MethodInfor("join", "()v"));
		return methods2Tran;
	}
}
