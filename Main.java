import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

/**    读取一个文件要路径。不能只有名字（捂脸哭）

已知：能处理该路径下 【包含至多一个目录✖】  【所有目录√】  的文件，但是“.class”文件行数必定为1？
     某些文件（a.cpp）他用Dev c++ 打开和用idea打开不一样？，idea方式正确

     【原先不能处理同文件夹（同属wc文件夹），能处理My_wc文件夹内文件（不能My_wc目录）,不能同级别（JavaSE？）
     idea能处理e:/java/jdk1.7.0_80/t  ??    cmd JVM虚拟机不能处理e:/java/jdk1.7.0_80/t ？？
     idea不能处理e:/java/jdk1.7.0_80/bin/Alian1.java ??  cmd JVM能处理e:/java/jdk1.7.0_80/bin/Alian1.java   ??
     经过设置硬盘安全模式：完全控制（能处理My_wc）】   文件得输入路径，不能只有名称

     这个Main类的注释行与blog上的作业定义不同。
     注释行可以同时为代码行，而blog上注释行不是代码行

     不存在的文件目录，为什么能输出   该路径算成文件？
 */

/**
 * @author  Mazin
 */
public class Main {

    public static void main(String[] args) throws Exception {
        print();
        Scanner sc = new Scanner(System.in);

        // 拿到文件数据流，用read()读取字符数,一个文件读到末尾就没法从头
        String paramter = sc.nextLine();
        String file_path = sc.nextLine();

        File f = new File(file_path.trim());

        //判断是否是目录
        judge_dir(f);

        // 判断目录的变量，在case里分支
        boolean isdirectory = false;

        System.out.println("--------------------------");

        String[] path = new String[200];

        // wc命令用string数组存储，也好拆分成每个
        String[] s = paramter.trim().split(" ");

        // 作输出选择
        switch_method(s,f,path,isdirectory);

        //  e:/develop/theme/My_wc/text.txt
        //  e:/develop/theme/test_wc
        //  e:/develop/theme/My_wc/test.java
        //  e:/develop/theme/My_wc/t.c

    }

    /**           开头目录             */
    public static void print(){
        System.out.println("输入命令\n-c:\t获取字符数\n-l:\t获取行数\n-w:\t获取单词数\n-a:\t获取其他特殊行数\n\t(代码行、注释行、空行)");
        System.out.println("\t编程源文件正常，其他的文件不一定全对");
        System.out.println("-s:\t递归处理目录下符合条件的文件[输入目录选择这里]\n\t【若选择此项，请将-s放在开头】\n");
    }

    /**            判断目录           */
    public static void judge_dir(File f) {
        System.out.print("判断此路径是否为目录\n");
        if (f.isDirectory()) {
            System.out.println("该路径为目录");
        } else {
            System.out.println("该路径为文件");
        }
    }

    /**          switch 做选择              */
    public static void switch_method(String[] s,File f,String[] path,boolean isdirectory) throws Exception{
        int n = 0, i = 0, count = 0;
        if (f.isDirectory()) {
            isdirectory = true;
            // 转化为路径
            path = path(f);
        }else {
            count = 1;
            System.out.println("文件路径：" + f.getPath());
        }

        while (n < s.length) {
            switch (s[n]) {
                case "-c": {
                    // 遍历出来的非目录文件名
                    if (isdirectory && "-s".equals(s[0])) {
                        count_char(path[i]);
                    }
                    // 不以"-s"开头，或者不是目录
                    else {
                        count_char(f.getPath());
                    }
                    break;
                }

                case "-w": {
                    if (isdirectory && "-s".equals(s[0])) {
                        count_word(path[i]);
                    }
                    else {
                        count_word(f.getPath());
                    }
                    break;
                }

                case "-l": {
                    if (isdirectory && "-s".equals(s[0])) {
                        count_row(path[i]);
                    }
                    else {
                        count_row(f.getPath());
                    }
                    break;
                }

                case "-a": {
                    if (isdirectory && "-s".equals(s[0])) {
                        count_else(path[i]);
                    }
                    else {
                        count_else(f.getPath());
                    }
                    break;
                }

                case "-s":
                    if (isdirectory) {
                        // 输出文件名
                        System.out.println("文件路径：" + path[i]);
                    }else{
                        System.out.println("【该路径为文件，-s无效】");
                    }
                    break;

                default:
            }
            // 执行完file_path下的每一个文件的所有wc命令
            if (n == s.length - 1 && isdirectory) {
                count++;
                // 判断file_path下的文件是否全部结束？
                if (path[++i] == null) {
                    break;
                }
                System.out.println("-------------------------");
                //  n=-1 下面的n++变成 n = 0，这样通过“-s”输出文件名
                n = -1;
            }
            n++;
        }
        System.out.println("----------------------");
        //统计
        System.out.println("总共" + count + "个文件");
    }

    /**    字符数，不含空格、回车、tab,   输出可显示字符       */
    public static void count_char(String s) throws Exception {
        FileReader in = new FileReader(s);
        int character_num = 0;
        while (true) {
            int c = in.read();
            if (c == -1) {
                break;
            }
            if (c != '\r' && c != ' ' && c != '\n' && c != '\t') {
                character_num++;
            }
        }
        System.out.println("文件字符数为" + character_num);
        in.close();
    }

    /**     行数     readline()读行  差点，回车结束读不出来    */
    public static void count_row(String s) throws Exception {
        FileReader in = new FileReader(s);
        int row_num = 1;
        int r = in.read();
        while (true) {
            // 接收的回车是  两个字节
            if(r == '\r') {
                // r = in.read()
                if (in.read() == '\n') {
                    row_num++;
                }
            }
                r = in.read();
                if (r == -1) {
                    break;
                }
        }

        System.out.println("文件行数为" + row_num);
        in.close();
    }

    /**       单词数，字母才认为一个单词，特殊字符分词         */
    public static void count_word(String s) throws Exception {
        FileReader in = new FileReader(s);
        int word_num = 0;
        char null_str = ' ';
        boolean flag = false;
        int w = in.read();
        while (true) {
            // 单词只记录 标识，其他字符组成不算单词
            boolean isword = false;
            // 避免多个空格连续
            while (w == null_str) {
                w = in.read();
            }
            t:
            //找到下一个空格前的都是同一个word
            while (w != null_str && w != -1) {
                // 数字 不算单词
                boolean word = (w >= 'A' && w <= 'Z') || (w >= 'a' && w <= 'z') || w == '$' || w == '_';
                if (word) {
                    if (!isword) {
                        isword = true;
                    }
                } else {
                    w = in.read();
                    break t;
                }
                w = in.read();
            }
            //读到文本末尾，如果前面是字母，那么+1.
            flag = isword;
            //出来的条件不是文件末尾，且是空格
            if (w != -1 && isword) {
                word_num++;
            }
            //worknum的计数是在遇到 空格、回车，计数前面连续的字符，若是最后读取-1，直接结束会漏掉一个
            if (w == -1) {
                break;
            }
        }
        if (flag){
            word_num++;
        }
        System.out.println("文件单词数为" + word_num);
        in.close();
    }

    /**          代码行、空行、注释行        */
    public static void count_else(String s) throws Exception {
        FileReader in = new FileReader(s);
        char null_str = ' ', tab_char = '\t', r_char = '\r';
        int code = 0, empty = 0, note = 0, count;
        boolean iscode = false;
        boolean isnote = false;
        int t = in.read();
        while (true) {
            //  第一个可显示字符  null_str = ' '   tab_char = '\t'
            while (t == null_str || t == tab_char) {
                t = in.read();
            }
            count = 0;

            // 非文尾 行末
            while (t != r_char && t != -1) {
                // 代码行，标识、代码 都是字母
                boolean judge1 = !isnote && !iscode && ((t >= 'A' && t <= 'Z') || (t >= 'a' && t <= 'z') || t == ';');
                if (judge1) {
                    code++;
                    iscode = true;
                }
                // 数可显示字符
                if (t != ' ' && t != '\t') {
                    count++;
                }
                // 多行注释（/*）和单行注释（//）
                boolean judge2 = t == '/' && !isnote && ((t=in.read())=='/'|| t=='*');
                if (judge2) {
                    isnote = true;
                    note++;
                }
                t = in.read();
            }              // while
            //  防止直接 回车空行      r_char='\r'
            if (t == r_char) {
                // 不大于1个可显示字符才算空行，且不是注释行，代码行
                if (!iscode && !isnote && count <= 1) {
                    empty++;
                }//  问题： count数到了注释行代表‘/’？
                // 未遇到 字母（代码行代表），注释行（'/'代表）       这种  })
                else if (!iscode && !isnote) {
                    code++;
                }
                // '\n'
                t = in.read();
                //下一行开头
                t = in.read();
            }

            // 到下一行，初始化
            iscode = false;
            isnote = false;
            // 到达这一步至少是 \r 回车后-1 肯定空行
            if (t == -1) {
                if(!iscode && !isnote) {
                    empty++;
                }
                break;
            }
        }          // true
        System.out.println("代码行数为" + code);
        System.out.println("空行数为" + empty);
        System.out.println("注释行数为" + note);
        in.close();
    }

    /**             路径转化              */
    public static String[] path(File f){
        int dir_num = 0, map_key = 0;
        String[] real_path = new String[200];
        HashMap<Integer,File> hm = new HashMap<Integer,File>();
        File[] list = f.listFiles();
        while(dir_num < list.length) {
            // 拿到输入 路径下的所有文件
            hm.put(map_key++, list[dir_num++]);
        }
        int count_dir = 0, count ,len = list.length;
        while (count_dir < len) {
            count = 0;
            File temp_file = hm.get(count_dir);
            if (temp_file.isDirectory()) {
                list = temp_file.listFiles();
                len += list.length;
                while (count < list.length) {
                    // 将该目录下的东西全部添加
                    hm.put(map_key++, list[count++]);
                }
            }
            count_dir++;
        }
        int i =0 ,j=0;
        //  将hash中属于文件的拿出来作为路径
        while(i<hm.size()) {
            if(!hm.get(i).isDirectory()) {
                real_path[j++] = hm.get(i).getPath();
            }
            i++;
        }
        return real_path;
    }
}