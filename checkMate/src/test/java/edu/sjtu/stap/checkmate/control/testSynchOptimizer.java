package edu.sjtu.stap.checkmate.control;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testSynchOptimizer {

	@Before
	public void setUp(){
		int thread=0;
		int monitor=1;
		Controller.acquireLock(thread, monitor);
		Controller.acquireLock(thread, monitor);
		Controller.wait(thread, monitor);
		Controller.releaseLock(thread);
		Controller.acquireLock(thread,monitor);
		Controller.acquireLock(thread, monitor);
		Controller.releaseLock(thread);
		Controller.releaseLock(thread);
		Controller.releaseLock(thread);
	}
	
	@Test
	public void testOptimize() {
		SynchOptimizer.optimize();
		List<String> result=AddLinesToTraceProgram.getInstance().getThrToLines().get(0L);
		assertEquals(5,result.size());
		assertEquals("synchronized(l1){",result.get(0));
		assertEquals("synchronized(l1){",result.get(1));
		assertEquals("l1.wait();\n",result.get(2));
		assertEquals("}\n",result.get(3));
		assertEquals("}\n",result.get(4));
	}

}
