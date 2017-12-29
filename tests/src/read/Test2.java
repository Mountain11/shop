package read;

/*
 * 现成程序中的Test类中的代码在不断地产生数据，然后交给TestDo.doSome()方法
 * 去处理，就好像生产者在不断地产生数据，消费者在不断消费数据。请将程序改造
 * 成有10个线程来消费生成者产生的数据，这些消费者都调用TestDo.doSome()方法
 * 去进行处理，故每个消费者都需要一秒才能处理完，程序应保证这些消费者线程依
 * 次有序地消费数据，只有上一个消费者消费完后，下一个消费者才能消费数据，下
 * 一个消费者是谁都可以，但要保证这些消费者线程拿到的数据是有顺序的
 */
public class Test2 {
	
	public static void main(String[] args) {
		
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		for(int i=0;i<10;i++){  //这行不能改动
			String input = i+"";  //这行不能改动
			String output = TestDo.doSome(input);
			System.out.println(Thread.currentThread().getName()+ ":" + output);
		}
	}
}

//不能改动此TestDo类
class TestDo {
	public static String doSome(String input){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String output = input + ":"+ (System.currentTimeMillis() / 1000);
		return output;
	}
}

