
public class ThreadWaiting {

	public static void main(String[] args) {
		
		
		Object o = new Object();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Runnable run1 = new Runnable() {

			@Override
			public void run() {
				try {
					synchronized (o) {
						System.out.println("thread running");
						Thread.sleep(5000);
						System.out.println("Thread resuming");
						o.notify();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		System.out.println("starting thread");
		Thread t1 = new Thread(run1);
		t1.start();
		
		
		
		long fire = System.currentTimeMillis() + 1000 * 5;
		
	
		
		synchronized (o) {
			try {
				o.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("main resumed");
		
		
		

	}

}
