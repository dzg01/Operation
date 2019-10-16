import java.util.*;

public class BasicWork {
	
	int fenzi;
	int fenmu;
	int zheng;
	
	public BasicWork() {
		
	}
	
	public BasicWork(int limit) {
//		fenzi=(int)(0+Math.random()*(limit-0+1));
//		zheng=(int)(0+Math.random()*(limit-0+1));
		zheng=0;
		fenmu=(int)(1+Math.random()*(limit-1+1));
		fenzi=(int)(Math.random()*fenmu*limit);
		reduction(this);
	}
	
	//用于测试
	public BasicWork(int a,int b,int c) {
		fenzi=a;
		fenmu=b;
		zheng=c;
	}
	
	//约分方法
	public static void reduction(BasicWork reop) {
		int re=gcd(reop.fenzi,reop.fenmu);
		re = re == 0 ? 1 : re;
	    reop.fenzi=reop.fenzi/re;
	    reop.fenmu=reop.fenmu/re;
	    //System.out.println(reop.fenzi + " " + reop.fenmu);
	    try {
			if(reop.fenzi>=reop.fenmu) {
				reop.zheng=reop.zheng+reop.fenzi/reop.fenmu;
				reop.fenzi=reop.fenzi%reop.fenmu;
				if(reop.fenzi==0) reop.fenmu=1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public BasicWork add(BasicWork a) {
		BasicWork temp=new BasicWork();
		temp.zheng=zheng+a.zheng;
	    temp.fenmu=fenmu*a.fenmu;
	    temp.fenzi=fenzi*a.fenmu+a.fenzi*fenmu;
	    //约分
	    reduction(temp);
		return temp;
	}
	
	public BasicWork sub(BasicWork s) {
		BasicWork temp=new BasicWork();
		temp.fenzi=fenzi+fenmu*zheng;
		temp.zheng=0;
		temp.fenmu=fenmu;
		s.fenzi=s.fenzi+s.fenmu*s.zheng;
		s.zheng=0;
		temp.fenzi=temp.fenzi*s.fenmu;
		s.fenzi=s.fenzi*temp.fenmu;
		temp.fenmu=temp.fenmu*s.fenmu;
		temp.fenzi=temp.fenzi-s.fenzi;
		//约分
		reduction(temp);
		return temp;
	}
	
	public BasicWork mul(BasicWork m) {
		BasicWork temp=new BasicWork();
		temp.fenzi=fenzi+fenmu*zheng;
		temp.zheng=0;
		temp.fenmu=fenmu;
		m.fenzi=m.fenzi+m.fenmu*m.zheng;
		m.zheng=0;
		temp.fenmu=temp.fenmu*m.fenmu;
		temp.fenzi=temp.fenzi*m.fenzi;
		//约分
		reduction(temp);
		return temp;
	}
	
	public BasicWork div(BasicWork d) {
		d.fenzi+=d.fenmu*d.zheng;
		d.zheng=0;
		int i;
		i=d.fenmu;
		d.fenmu=d.fenzi;
		d.fenzi=i;
		return this.mul(d);
	}
	
	public boolean equals(BasicWork rhs) {
		return (zheng*fenmu+fenzi)*rhs.fenmu == (rhs.zheng*rhs.fenmu+rhs.fenzi)*fenmu;
	}
	
	private static int gcd(int a,int b) {
		if(b==0) return a;
		return gcd(b,a%b);
	}
	
	public String toString() {
		String s=new String();
		if(fenzi==0) s=zheng+"";
		else if(zheng==0) s=fenzi+"/"+fenmu;
		else s=zheng+"'"+fenzi+"/"+fenmu;
		return s;
	}
	
	//命令行传入参数转换为整型
	public static int toInt(String s) {
		int ans=0;
		for(int i=0;i<s.length();i++) {
			ans=ans*10+s.charAt(i)-'0';
		}
		return ans;
	}
	
	public static BasicWork toBasicWork(String str) {
		BasicWork temp = new BasicWork();
		ArrayList<Integer> ary = new ArrayList<Integer>();
		int tmp = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9') tmp = tmp*10+str.charAt(i)-'0';
			else {
				ary.add(tmp);
				tmp = 0;
			}
		}
		ary.add(tmp);
		if(ary.size() == 1) {
			temp.zheng = ary.get(0);
			temp.fenzi = 0;
			temp.fenmu = 1;
		}
		else if(ary.size() == 2) {
			temp.zheng = 0;
			temp.fenzi = ary.get(0);
			temp.fenmu = ary.get(1);
		}
		else {
			temp.zheng = ary.get(0);
			temp.fenzi = ary.get(1);
			temp.fenmu = ary.get(2);
		}
		return temp;
	}
	
//	public static void main(String[] args) {
//		Scanner in = new Scanner(System.in);
//		int f;
//		int a, b, c;
//		BasicWork bb=new BasicWork(1,2,3);
//		System.out.println(bb.toString());
//		while(in.hasNext()){
//			f = in.nextInt();
//			a = in.nextInt();
//			b = in.nextInt();
//			c = in.nextInt();
//			BasicWork A = new BasicWork(a,b,c);
//			a = in.nextInt();
//			b = in.nextInt();
//			c = in.nextInt();
//			BasicWork B = new BasicWork(a,b,c);
//			BasicWork C = new BasicWork(0,0,0);
//			if(f==1) 
//				C=A.add(B);
//			if(f==2) C=A.sub(B);
//			if(f==3) C=A.mul(B);
//			if(f==4) C=A.div(B);
//			System.out.println(C.zheng+" "+C.fenzi+" "+C.fenmu);
//			
//		}
//		
//	}
}
