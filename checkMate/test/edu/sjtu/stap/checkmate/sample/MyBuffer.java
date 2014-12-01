package edu.sjtu.stap.checkmate.sample;

import java.util.ArrayList;
import java.util.List;

import edu.sjtu.stap.checkmate.AddLinesToTraceProgram;
import edu.sjtu.stap.checkmate.ConditionAnnotation;
import edu.sjtu.stap.checkmate.control.Controller;

/**
 * This class is defined as an example in the paper at FSE'10
 * @author Ziyi
 *
 */
public class MyBuffer {

	private List buf=new ArrayList();
	private int cursize=0, maxsize;
	private ConditionAnnotation condition=new ConditionAnnotation(this){
		public boolean isConditionTrue(){
			return ((MyBuffer)o).isFull();
		}
	};
	
	public MyBuffer(int max){
		maxsize=max;
	}
	
	public synchronized void put(Object elem){
		//Controller.acquireLock(this);
		condition.waitBegin(this);
		for(int i=0;i<10000;i++){
			for(int j=0;j<10000;j++){
				int k=i+j;
			}
		}
		//Controller.writeOrCall(this);
		while(isFull()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		condition.waitEnd();
		buf.add(elem);
		cursize++;
		//Controller.writeOrCall(this);
		//Controller.notify(this);
		notify();
		//Controller.releaseLock();
	}
	
	public Object get(){
		Object elem;
		synchronized(this){
			//Controller.acquireLock(this);
			while(isEmpty()){
				try {
				//	Controller.wait(this);
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			elem=buf.remove(0);
			//Controller.releaseLock();
		}
		synchronized(this){
			//Controller.acquireLock(this);
			condition.notifyBegin(this);
			if(isFull()){
				cursize--;
				//Controller.writeOrCall(this);
				notify();
			}else{
				cursize--;
				//Controller.writeOrCall(this);
			}
			condition.notifyEnd();
			//Controller.releaseLock();
		}
		return elem; 
	}
	
	public synchronized void resize(int max){
		//Controller.acquireLock(this);
		maxsize=max;
		//Controller.writeOrCall(this);
		//Controller.releaseLock();
	}
	
	public synchronized boolean isFull(){
		//Controller.acquireLock(this);
		//Controller.releaseLock();
		return (cursize>=maxsize);
		
	}
	
	public synchronized boolean isEmpty(){
		//Controller.acquireLock(this);
		//Controller.releaseLock();
		return (cursize==0);
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		final MyBuffer bf=new MyBuffer(1);
		Thread p=new Thread(){
			public void run(){
				for(int i=0;i<2;i++){
					bf.put(new Integer(i));
				}
			}
		};
		Thread r=new Thread(){
			public void run(){
				bf.resize(10);
			}
		};
		Thread c =new Thread(){
			public void run(){
				bf.get();
			}
		};
		p.start();
		//Controller.start(p);
		r.start();
		//Controller.start(r);
		c.start();
	//	Controller.start(c);
		
		//Controller.join(p);
		//Controller.join(r);
		//Controller.join(c);
		p.join();
		r.join();
		c.join();
		
		//System.out.println(Controller.createTraceProgram());
	}
}