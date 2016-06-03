package filegraph.utl.parallel;

import java.util.concurrent.RecursiveAction;

public class ParaTaskTest2 extends RecursiveAction {
	private static final long serialVersionUID = -8539695660104545352L;
	private String thread_name;

	public ParaTaskTest2(String thread_name) {
		this.thread_name = thread_name;
	}

	@Override
	protected void compute() {
		try {
			System.out.println("Thread " + thread_name + " start!");
			Thread.sleep(10000);
			System.out.println("Thread " + thread_name + " end!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
