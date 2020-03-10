import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

/*    读取一个文件要路径。不能只有名字（捂脸哭）
      缺陷：该目录下，包含目录1，目录1不能再包含目录2,否则查不了,最后一个方法

已知：只能处理该路径下 【包含至多一个目录】 的“.class”文件，“.class”文件行数必定为1？
     某些文件（a.cpp）他用Dev c++ 打开和用idea打开不一样，idea方式正确

     原先不能处理同文件夹（同属wc文件夹），能处理My_wc文件夹内文件（不能My_wc目录）,不能同级别（JavaSE？）
     idea能处理e:/java/jdk1.7.0_80/t  ??    cmd JVM虚拟机不能处理e:/java/jdk1.7.0_80/t ？？
     idea不能处理e:/java/jdk1.7.0_80/bin/Alian1.java ??  cmd JVM能处理e:/java/jdk1.7.0_80/bin/Alian1.java   ??
     经过设置硬盘安全模式：完全控制（能处理My_wc）

     这个Main类的注释行与blog上的作业定义不同。
     注释行可以同时为代码行，而blog上注释行不是代码行

     不存在的文件目录，为什么能输出   该路径是文件？
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("输入命令\n-c:获取字符数\n-l:获取行数\n-w:获取单词数\n-a:获取其他特殊行数\n(代码行、注释行、空行)");
        System.out.println("编程源文件正常，其他的文件不一定全对");
        System.out.println("-s:递归处理目录下符合条件的文件\t【若选择此项，请将-s放在开头】\n");
        Scanner sc = new Scanner(System.in);
        // 拿到文件数据流，用read()读取字符数,一个文件读到末尾就没法从头
        String paramter = sc.nextLine();
        String file_path = sc.nextLine();
        File f = new File(file_path);

        boolean isdirectory = false;          // 判断目录的变量，在case里分支

        //     两个Vector对象，一个用于 进入方法中存储，另一个在主函数接收
        Vector v = new Vector(10, 10);
        Vector return_Vector = new Vector(10, 10);

        System.out.print("判断此路径是否为目录\n");
        if (f.isDirectory())
            System.out.println("该路径为目录");
        else System.out.println("该路径为文件");

        System.out.println("--------------------------");

        String[] path = new String[50];            //  10个文件

        if (f.isDirectory()) {
            isdirectory = true;
            return_Vector = FileDir(f, v);      // 完全遍历所有文件
            path = path(f,return_Vector);         // 转化为路径
        }else
            System.out.println(f.getName());


        // wc命令用string数组存储，也好拆分成每个
        String[] s = paramter.trim().split(" ");
        int n = 0, i = 0;
        while (n < s.length) {
            switch (s[n]) {

                case "-c": {
                    if (isdirectory && s[0].equals("-s"))      // 遍历出来的非目录文件名
                        count_char(path[i]);
                    else                        // 不以"-s"开头，或者不是目录
                        count_char(f.getPath());
                    break;
                }

                case "-w": {
                    if (isdirectory && s[0].equals("-s"))
                        count_word(path[i]);
                    else
                        count_word(f.getPath());
                    break;
                }

                case "-l": {
                    if (isdirectory && s[0].equals("-s"))
                        count_row(path[i]);
                    else
                        count_row(f.getPath());
                    break;
                }

                case "-a": {
                    if (isdirectory && s[0].equals("-s"))
                        count_else(path[i]);
                    else
                        count_else(f.getPath());
                    break;
                }

                case "-s":
                    System.out.println("文件名："+path[i]);  // 输出文件名
                    break;
            }
            if (n == s.length - 1) {       // 执行完file_path下的每一个文件的所有wc命令
                if (path[++i] == null)      // 判断file_path下的文件是否全部结束？
                    break;
                System.out.println("-------------------------");
                n = -1;         //  n=-1 下面的n++变成 n = 0，这样通过“-s”输出文件名
            }
            n++;
        }
        //  e:/develop/theme/My_wc/text.txt
        //  e:/develop/theme/test_wc
        //  e:/develop/theme/My_wc/test.java
        //  e:/develop/theme/My_wc/t.c
    }

    // 字符数，不含空格、回车、tab
    public static void count_char(String s) throws Exception {
            FileReader in = new FileReader(s);
        int character_num = 0;
        while (true) {
            int c = in.read();
            if (c == -1)
                break;
            if (c != '\r' && c != ' ' && c != '\n' && c != '\t')
                character_num++;
        }
        System.out.println("文件字符数为" + character_num);
        in.close();
    }

    // 行数
    public static void count_row(String s) throws Exception {
        FileReader in = new FileReader(s);
   //     BufferedReader reader = new BufferedReader(in);
        int row_num = 1;
        int r = in.read();
        while (true) {
            if (r == '\r')                    // 接收的回车是  两个字节
                if ((r = in.read()) == '\n')
                    row_num++;
            r = in.read();
            if (r == -1)
                break;
        }
/*
        String str = reader.readLine();    经测试，readline()读一行，但是\r\n
        while(str != null){                 \r在一行末尾 \n在下一行开头。然而，readline()却读了两行。日
            row_num++;
            str = reader.readLine();
        }
*/
        System.out.println("文件行数为" + row_num);
        in.close();
  //      reader.close();
    }

    // 单词数，字母才认为一个单词，特殊字符分词
    public static void count_word(String s) throws Exception {
        FileReader in = new FileReader(s);
        int word_num = 0;
        boolean flag = false;
        int w = in.read();
        while (true) {
            boolean isword = false;                  // 单词只记录 标识，其他字符组成不算单词
            while (w == ' ')      // 避免多个空格连续
                w = in.read();
            t:
            while (w != ' ' && w != -1) {      //找到下一个空格前的都是同一个word
                if ((w >= 'A' && w <= 'Z') || (w >= 'a' && w <= 'z') || w == '$' || w == '_') {  // 数字 不算单词
                    if (!isword)
                        isword = true;
                } else {
                    w = in.read();
                    break t;
                }
                w = in.read();
            }
            flag = isword;          //读到文本末尾，如果前面是字母，那么+1.
            if (w != -1 && isword)               //出来的条件不是文件末尾，且是空格
                word_num++;
            if (w == -1)      //worknum的计数是在遇到 空格、回车，计数前面连续的字符，若是最后读取-1，直接结束会漏掉一个
                break;
        }
        if (flag) word_num++;
        System.out.println("文件单词数为" + word_num);
        in.close();
    }

    // 代码行、空行、注释行
    public static void count_else(String s) throws Exception {
        FileReader in = new FileReader(s);
        int code = 0, empty = 0, note = 0, count;
        int t = in.read();
        while (true) {
            while (t == ' ' || t == '\t')
                t = in.read();   //  第一个可显示字符
            count = 0;
            boolean iscode = false;
            boolean isnote = false;
            while (t != '\r' && t != -1) {    // 非文尾 行末
                if (!isnote && !iscode && ((t >= 'A' && t <= 'Z') || (t >= 'a' && t <= 'z') || t == ';')) {// 代码行，标识、代码 都是字母
                    code++;
                    iscode = true;
                }
                if (t != ' ' && t != '\t')     // 数可显示字符
                    count++;
                // t.inread();
                if (t == 47 && !isnote && ((t=in.read())=='/'|| t=='*')) {   // 多行注释（/*）和单行注释（//）
                    isnote = true;
                    note++;
                }
                t = in.read();
            }              // while
            if (t == '\r') {          //  防止直接 回车空行
                if (!iscode && !isnote && count <= 1)     // 不大于1个可显示字符才算空行，且不是注释行，代码行
                    empty++;                               //  问题： count数到了注释行代表‘/’？
                else if (!iscode && !isnote)             // 未遇到 字母（代码行代表），注释行（'/'代表）
                    code++;
                t = in.read();        // '\n'
                t = in.read();       //下一行开头
            }
            if (t == -1) {    // 到达这一步至少是 \r 回车后-1 肯定空行
                if(!iscode && !isnote)
                    empty++;
            break;
            }
        }          // true
        System.out.println("代码行数为" + code);
        System.out.println("空行数为" + empty);
        System.out.println("注释行数为" + note);
        in.close();
    }

    // 文件遍历
    public static Vector FileDir(File dir, Vector file_name) {
        File[] f = dir.listFiles();         /*    listFiles()返回该目录下的所有文件与目录    */
        for (File file : f) {             /*   有子目录(含文件或目录)则递归直到得到所有文件名和目录名  */
            if (file.isDirectory()) {
                file_name.add(file.getName());
                FileDir(file, file_name);                                //递归
            }
            if (!file.isDirectory())
                file_name.add(file.getName());
        }
        file_name.add(" ");
        return file_name;
    }

    //  路径转化,        file文件的canread() ？
    public static String[] path(File f,Vector file_name){
        Object[] all_file = file_name.toArray();
        int i = 0 , j = 0;
        String[] real_path = new String[50];     // 固定长度文件...哎
        String temp = new String();
        while(i < all_file.length) {
            File turn_path = new File(f.getPath() + "\\" + all_file[i].toString());
            if (turn_path.isDirectory()) {
                if(!all_file[i].equals(" "))      // 不是以“ ”进来被认为的目录
                    temp = "\\" + all_file[i].toString();
                else temp="";
            }
            else
                real_path[j++] = f.getPath()+ temp +"\\" + all_file[i].toString();
            i++;
        }
        return real_path;
    }
}