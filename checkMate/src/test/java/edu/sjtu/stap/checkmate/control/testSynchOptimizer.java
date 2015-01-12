package edu.sjtu.stap.checkmate.control;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testSynchOptimizer {

	@Before
	public void setUp(){
		int thread=0;
		int monitor1=1;
		int monitor2=2;
		Controller.acquireLock(thread, monitor1);
		Controller.acquireLock(thread, monitor1);
		Controller.wait(thread, monitor1);
		Controller.releaseLock(thread);
		Controller.acquireLock(thread,monitor1);
		Controller.acquireLock(thread, monitor1);
		Controller.releaseLock(thread);
		Controller.acquireLock(thread, monitor2);
		Controller.releaseLock(thread);
		Controller.releaseLock(thread);
		Controller.releaseLock(thread);
	}
	
	@Test
	public void testOptimize() {
		SynchOptimizer.optimize();
		List<String> result=AddLinesToTraceProgram.getInstance().getThrToLines().get(0L);
		assertEquals(9,result.size());
		assertEquals("synchronized(l1){",result.get(0));
		assertEquals("synchronized(l1){",result.get(1));
		assertEquals("l1.wait();\n",result.get(2));
		assertEquals("}\n",result.get(3));
		assertEquals("synchronized(l1){",result.get(4));
		assertEquals("synchronized(l2){",result.get(5));
		assertEquals("}\n",result.get(6));
		assertEquals("}\n",result.get(7));
		assertEquals("}\n",result.get(8));
	}

}
