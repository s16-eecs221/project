package filegraph.utl.parallel;

import java.util.concurrent.RecursiveTask;

public class ParaTaskTest extends RecursiveTask<Integer> {
	private static final long serialVersionUID = -8539695660104545352L;
	private String thread_name;
	private byte[] data;
	private int start;
	private int end;

	public ParaTaskTest(String thread_name, int start, int end, byte[] data) {
		this.thread_name = thread_name;
		this.start = start;
		this.end = end;
		this.data = data;
	}

	@Override
	protected Integer compute() {
		data[start] = -1;
		String data_str = "";
		for (int i = start; i < end; i++)
			data_str += data[i] + " ";
		System.out.println(thread_name + " data:" + data_str);
		return 1;
	}

}
