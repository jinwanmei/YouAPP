package j.jave.framework.servicehub;

import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.logging.JLoggerFactory;

import java.util.AbstractQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * the class describers a structure of a single queue on which a main thread scans , and poll a head element to a handler {@link Handler} 
 * to process. 
 * @author J
 *
 * @param <T>
 */
public class JQueueDistributeProcessor<T> {
	
	static class JQueueDistributeProcessorConfig{
		/*
		 * configure
		 */
		
		private String name;
		
		private int countPerSec=100;
		
		private int fixedThreadCount=10;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getCountPerSec() {
			return countPerSec;
		}

		public void setCountPerSec(int countPerSec) {
			this.countPerSec = countPerSec;
		}

		public int getFixedThreadCount() {
			return fixedThreadCount;
		}

		public void setFixedThreadCount(int fixedThreadCount) {
			this.fixedThreadCount = fixedThreadCount;
		}
	}
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private final AbstractQueue<T> executions;
	
	private Handler<T> handler;
	
	/*
	 * configure begin
	 */
	private int countPerSec=100;
	
	private int fixedThreadCount=10;
	
	private String name="";
	/*
	 * configure end
	 */
	
	private void initConfig(JQueueDistributeProcessorConfig config){
		this.name=config.name;
		this.countPerSec=config.countPerSec;
		this.fixedThreadCount=config.fixedThreadCount;
	}
	
	public JQueueDistributeProcessor(AbstractQueue<T> executions,JQueueDistributeProcessorConfig config){
		this.executions=executions;
		initConfig(config);
		setup();
	}
	
	public JQueueDistributeProcessor(AbstractQueue<T> executions,Handler<T> handler,JQueueDistributeProcessorConfig config){
		this.executions=executions;
		this.handler=handler;
		initConfig(config);
		setup();
	}
	
	private LinkedBlockingQueue<Runnable> linkedBlockingQueue=new LinkedBlockingQueue<Runnable>();
	
	private ThreadPoolExecutor taskThreadPoolExecutor =null;
	
	public ThreadPoolExecutor getTaskThreadPoolExecutor() {
		if(taskThreadPoolExecutor==null){
			taskThreadPoolExecutor =new ThreadPoolExecutor
					(fixedThreadCount, fixedThreadCount, 1, TimeUnit.SECONDS, linkedBlockingQueue,new ThreadPoolExecutor.CallerRunsPolicy());
		}
		return taskThreadPoolExecutor;
	}
	
	Runnable scanOneByOneTask=new Runnable() {
		@Override
		public void run() {
			try{
				while(true){
					//sleep 1 second.
					Thread.sleep(1000);
					
					if(executions.size()>0){
						// do with the head elements in the EventQueue every 1 second.
						for(int i=0;i<countPerSec;i++){
							if(executions.size()>0){
								final T execution= executions.poll();
								if(handler.isLaterProcess(execution,executions)){
									executions.offer(execution);
								}
								else{
									Runnable futureTask= handler.taskProvided(execution, executions);
									if(futureTask!=null){
										getTaskThreadPoolExecutor().execute(futureTask);
									}
									handler.postProcess(execution, executions);
								}
							}
						}
					}
				}
			}catch(InterruptedException e){
				LOGGER.error("Interrupted by any thread: ",e);
				LOGGER.info("Resume the thread. ");
				// resume the task. 
				scanOneByOneExecutor.execute(scanOneByOneTask);
			}
			catch(Exception e){
				LOGGER.error("Event Main Thread occurs an exception by : ",e);
				LOGGER.info("Resume the thread. ");
				// resume the task. 
				scanOneByOneExecutor.execute(scanOneByOneTask);
			}
			catch(Throwable e){
				LOGGER.error("Event Main Thread occurs an exception by : ",e);
				LOGGER.info("Resume the thread. ");
				throw e;
			}
		}
	};
	/**
	 * scan all {@link #eventQueue} to pop a highest event to execute.
	 */
	private ExecutorService scanOneByOneExecutor =Executors.newFixedThreadPool(1,new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, getName()+"-ScanOneByOne");
		}
	});

	private void setup(){
		scanOneByOneExecutor.execute(scanOneByOneTask);
	}
	
	/**
	 * How to process element polled from the queue.
	 * @author J
	 *
	 * @param <T>
	 */
	interface Handler<T>{
		
		/**
		 * offer the element again if true , otherwise processing the element and drop it.
		 * @param execution
		 * @param eventExecutions
		 * @return
		 */
		boolean isLaterProcess(T execution,AbstractQueue<T> eventExecutions);
		
		/**
		 * the method called base on the true returned from {@link #isLaterProcess(Object, AbstractQueue)}
		 * @param execution
		 * @param eventExecutions
		 * @return
		 */
		Runnable taskProvided(T execution,AbstractQueue<T> eventExecutions);
	
		/**
		 * the method called base on the true returned from {@link #isLaterProcess(Object, AbstractQueue)}
		 * @param execution
		 * @param eventExecutions
		 */
		void postProcess(T execution,AbstractQueue<T> eventExecutions);
		
	}

	public String getName() {
		return name;
	}
	
	
	public void addExecution(T execute){
		executions.offer(execute);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setHandler(Handler<T> handler) {
		this.handler = handler;
	}
	
}
