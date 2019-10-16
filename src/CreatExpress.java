
public class CreatExpress {
	static BasicWork a[]=new BasicWork[4];
	static char s[]=new char[3];
	//产生随机运算符
	public static char getSymbol() {
		char symbol[]={'+','-','×','÷'};
		int a=(int)(0+Math.random()*(3-0+1));
		return symbol[a];
	}
	//产生用户指定范围的数值
	public static int getNum(int num) {
		return (int)(1+Math.random()*(num-1+1));
	}
	//操作数个数
	public static int getOpnum() {
		return (int)(2+Math.random()*(4-2+1));
	}
	
	
	//生成一条式子
	public static String express(int limit,int opnum) {
		String str = null;
		for(int i=0;i<opnum;i++) {
			a[i]=new BasicWork(limit);
			BasicWork.reduction(a[i]);
			
		}
		if(opnum == 2) {
			str = a[0].toString() + getSymbol() + a[1].toString();
		}
		else if(opnum == 3) {
			int randx = (int)(Math.random()*10);
			if(randx == 0) 
				str = "("+a[0].toString() + getSymbol() + a[1].toString() + ")" + getSymbol() + a[2].toString();
			else if(randx == 1)
				str = a[0].toString() + getSymbol() + "(" + a[1].toString() + getSymbol() + a[2].toString() + ")";
			else 
				str = a[0].toString() + getSymbol() + a[1].toString() + getSymbol() + a[2].toString();
			
		}
		else {
			int randx = (int)(Math.random()*30);
			if(randx == 0)
				str = "(" + a[0].toString() + getSymbol() + a[1].toString() + getSymbol() + a[2].toString() + ")" 
				+ getSymbol() + a[3].toString();
			else if(randx == 1)
				str = a[0].toString() + getSymbol() + "(" + a[1].toString() + getSymbol() + a[2].toString() + ")"
				+ getSymbol() + a[3].toString();
			else if(randx == 2)
				str = "(" + a[0].toString() + getSymbol() + "(" + a[1].toString() + getSymbol() + a[2].toString() + "))"
				+ getSymbol() + a[3].toString();
			else if(randx == 3)
				str = a[0].toString() + getSymbol() + "(" + a[1].toString() + getSymbol() + "(" + a[2].toString()
				+ getSymbol() + a[3].toString() + "))";
			else if(randx == 4)
				str = "(" + a[0].toString() + getSymbol() + a[1].toString() + ")" + getSymbol() + "(" + a[2].toString()
				+ getSymbol() + a[3].toString() + ")";
			else 
				str = a[0].toString() + getSymbol() + a[1].toString() + getSymbol() + a[2].toString()
					+ getSymbol() + a[3].toString();
		}
		return str;
		
	}

}
