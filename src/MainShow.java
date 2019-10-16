import java.io.*;
import java.util.*;

public class MainShow {

	static String ProblemPath;  //问题文件路径
	static String AnswerPath;   //答案文件路径
	static int num;    //生成题目数量
	static int limit;   //题目数字范围
	static int opnum;	//操作数个数
	
	public static void main(String[] args) throws Exception {
		long StartTime = System.currentTimeMillis();
        if(args.length!=4) {
        	System.out.println("请正确输入！");
        	System.out.println("使用 -n 参数控制生成题目的个数   -r 参数控制题目中数值（自然数、真分数和真分数分母）的范围 ");
        	System.out.println("使用-e <exercisefile>.txt -a <answerfile>.txt统计答案对错");
        }
        else if(args[0].equals("-n")) {
        	num=BasicWork.toInt(args[1]);
            limit=BasicWork.toInt(args[3]);
            int cnt = 0;
            File problemFile = new File("Exercises.txt");
            File answerFile = new File("Answers.txt");
            FileWriter problemWriter = new FileWriter(problemFile);
            FileWriter answerWriter = new FileWriter(answerFile);
			while(cnt < num) {
				String str = CreatExpress.express(limit, CreatExpress.getOpnum());
				BasicWork ans = check(str);
				//System.out.println(str);
				if(ans != null) {
					cnt++;
					//放入文件保存
					problemWriter.append(cnt+"."+str+"\r\n");
					//生成答案文件
					answerWriter.append(cnt+"."+ans.toString()+"\r\n");
				}
			}
			problemWriter.close();
			answerWriter.close();
        }	
        else if(args[0].equals("-e")) {
        	ArrayList<Integer> right = new ArrayList<Integer>();	//存答案正确的题号
        	ArrayList<Integer> wrong = new ArrayList<Integer>();
        	int cnt = 1;
        	ProblemPath=args[1];
            AnswerPath=args[3];
            Scanner problemIn = new Scanner(new File(ProblemPath));
            Scanner answerIn = new Scanner(new File(AnswerPath));
            while(problemIn.hasNextLine()) {
            	String pStr = problemIn.nextLine();
            	String aStr = answerIn.nextLine();
            	String Pstr[] = pStr.trim().split("\\.");	//"1.问题": 以“.”分开，取得问题
            	String Astr[] = aStr.trim().split("\\.");	//"1.答案": 以“.”分开， 取得答案
            	BasicWork xx = check(Pstr[1]);
            	BasicWork yy = BasicWork.toBasicWork(Astr[1]);
            	if(xx == null || !xx.equals(yy)) {
            		wrong.add(cnt);
            	}
            	else {
            		right.add(cnt);
            	}
            	cnt++;
            }
            problemIn.close();
            answerIn.close();
            File grade = new File("Grade.txt");			//答案正确或错误存入文件
            FileWriter gradeWriter = new FileWriter(grade);
            gradeWriter.append("Corret: " + right.size() + "(");
            for(int i = 0; i < right.size(); i++) {
            	if(i != 0) gradeWriter.append(" ");
            	gradeWriter.append(right.get(i)+"");
            }
            gradeWriter.append(")\r\n");
            gradeWriter.append("Wrong: " + wrong.size() + "(");
            for(int i = 0; i < wrong.size(); i++) {
            	if(i != 0) gradeWriter.append(" ");
            	gradeWriter.append(wrong.get(i)+"");
            }
            gradeWriter.append(")\r\n");
            gradeWriter.close();
        }
        else if(args[0].equals("end")) return ;
		/**************************************************/
		long EndTime = System.currentTimeMillis();
		System.out.println(EndTime-StartTime + "(ms)");
	}
	
	
	//判断表达式是否合法，是否重复
	public static BasicWork check(String s) {
		boolean flag = true;
		String symbol[] = new String[100];
		int sym = 0;
		String number[] = new String[100];
		int num = 0;
		String str = "";
		//计算、判断合法、判断重复
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '×' || s.charAt(i) == '÷'
					|| s.charAt(i) == '(' || s.charAt(i) == ')') {
				if(!str.equals("")) {
					number[num++] = str;
					str = "";
				}
				if(s.charAt(i) == '+') {
					while(sym != 0 && !symbol[sym-1].equals("(")) {
						number[num++] = symbol[sym-1];
						sym--;
					}
					symbol[sym++] = "+";
				}
				else if(s.charAt(i) == '-') {
					while(sym != 0 && !symbol[sym-1].equals("(")) {
						number[num++] = symbol[sym-1];
						sym--;
					}
					symbol[sym++] = "-";
				}
				else if(s.charAt(i) == '×') {
					while(sym != 0 && (symbol[sym-1].equals("×") || symbol[sym-1].equals("÷"))) {
						number[num++] = symbol[sym-1];
						sym--;
					}
					symbol[sym++] = "×";
				}
				else if(s.charAt(i) == '÷') {
					while(sym != 0 && (symbol[sym-1].equals("×") || symbol[sym-1].equals("÷"))) {
						number[num++] = symbol[sym-1];
						sym--;
					}
					symbol[sym++] = "÷";
				}
				else if(s.charAt(i) == '(') {
					symbol[sym++] = "(";
				}
				else if(s.charAt(i) == ')') {
					while(sym != 0 && !symbol[sym-1].equals("(")) {
						number[num++] = symbol[sym-1];
						sym--;
					}
					if(sym != 0 && symbol[sym-1].equals("(")) sym--;
				}
			}
			else {
				str += s.charAt(i);
			}
		}
		BasicWork tempA, tempB;
		if(!str.equals("")) number[num++] = str;
		while(sym > 0) {
			number[num++] = symbol[sym-1];
			sym--;
		}
//		for(int i = 0; i < num; i++) System.out.print(number[i]+" ");
//		System.out.println();
		Stack<BasicWork> stk = new Stack<BasicWork>();
		for(int i = 0; i < num; i++) {
			if(number[i].equals("+")) {
				tempA = stk.peek();
				stk.pop();
				tempB = stk.peek();
				stk.pop();
				tempA = tempB.add(tempA);
				stk.push(tempA);
				
			}
			else if(number[i].equals("-")) {
				tempA = stk.peek();
				stk.pop();
				tempB = stk.peek();
				stk.pop();
				tempA = tempB.sub(tempA);
				stk.push(tempA);
				if(tempA.zheng < 0 || tempA.fenzi < 0||tempA.fenmu<0) {
					flag = false;
					break;
				}
			}
			else if(number[i].equals("×")) {
				tempA = stk.peek();
				stk.pop();
				tempB = stk.peek();
				stk.pop();
				tempA = tempB.mul(tempA);
				stk.push(tempA);
				if(tempA.zheng < 0 || tempA.fenzi < 0) {
					flag = false;
					break;
				}
			}
			else if(number[i].equals("÷")) {
				tempA = stk.peek();
				stk.pop();
				tempB = stk.peek();
				stk.pop();
				if(tempA.zheng == 0 && tempA.fenzi == 0) {
					flag = false;
					break;
				}
				tempA = tempB.div(tempA);
				stk.push(tempA);
				if(tempA.zheng < 0 || tempA.fenzi < 0) {
					flag = false;
					break;
				}
			}
			else {
				stk.push(BasicWork.toBasicWork(number[i]));
			}
		}
		if(flag == false) return null;
		else return stk.peek();
	}
}
