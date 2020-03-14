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
 注释行可以同时为代码行，而blog上注释行不是代码行     不规范写法也算入

 -x能处理：目录，文件，【就是不能总计文件个数，不过也是我自己加的】，-x调用的是Java GUI界面  额

 不存在的文件目录，为什么能输出   该路径算成文件？
 */

/**
 * @author  Mazin
 */
public class WC {
    public static void main(String[] args) throws Exception {
        Print();

        Scanner sc = new Scanner(System.in);
        String paramter = sc.nextLine();

        String constant = "-x";

        // wc命令用String数组存储，也好拆分成每个
        String[] s = paramter.trim().split(" ");

        File f = new File("");
        if(!constant.equals(s[0])) {
            String  FilePath = sc.nextLine();
            f = new File(FilePath.trim());
            //判断是否是目录
            JudgeIsDir(f);
        }
        // 判断是否为目录的变量，在case里分支
        boolean isDirectory = false;

        System.out.println("--------------------------");

        //  可能存在目录下文件太多，数组越界
        String[] path = new String[1000];

        // 作输出选择
        SwitchMethod(s,f,path,isDirectory);

        //  e:/develop/theme/My_wc/text.txt
        //  e:/develop/theme/My_wc/test_wc
        //  e:/develop/theme/My_wc/test.java
        //  e:/develop/theme/My_wc/t.c

    }

    /**           开头目录             */
    public static void Print(){
        System.out.println("输入命令\n-c:\t获取字符数\n-l:\t获取行数\n-w:\t获取单词数\n-a:\t获取其他特殊行数\n\t(代码行、注释行、空行)");
        System.out.println("\t编程源文件正常，其他的文件不一定全对");
        System.out.println("-s:\t递归处理目录下符合条件的文件[输入目录选择这里]\n\t【若选择此项，请将-s放在开头】\n");
        System.out.println("-x:\t弹窗选择文件，配合其他通配符使用");
    }

    /**            判断目录           */
    public static void JudgeIsDir(File f) {
        System.out.print("判断此路径是否为目录\n");
        if (f.isDirectory()) {
            System.out.println("该路径为目录");
        } else {
            System.out.println("该路径为文件");
        }
    }

    /**          switch 做选择              */
    public static void SwitchMethod(String[] s,File f,String[] path,boolean isDirectory) throws Exception{
        int n = 0;
        int i = 0;
        int count = 0;
        boolean isEnd = true;
        String constant1 = "-s";
        String constant2 = "-x";
        //  后面的条件是为了输出文件个数，count正确
        if (f.isDirectory()) {
            isDirectory = true;
            // -x单独运行，不做路径转化, s[0]必须为-s
            if(constant1.equals(s[0])){
                // 转化为路径
                path = Path(f);
            }
        }else if(!constant2.equals(s[0])){
            count = 1;
            System.out.println("文件路径：" + f.getPath());
        }

        while (n < s.length) {
            switch (s[n]) {
                case "-x":
                    if(isEnd) {
                        isEnd = false;
                        JFileChooser jfc = new JFileChooser("e:");
                        // 文件目录都可以
                        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        // 允许多文件打开
                        jfc.setMultiSelectionEnabled(true);
                        // jfc调用方法，弹出jfc窗口，jfc又设置文件对象f
                        jfc.showOpenDialog(jfc);
//                        File file = new File(jfc.getSelectedFile().getPath());
//                        path = Path(file);
                        f = new File(jfc.getSelectedFile().getPath());
                        if(f.isDirectory()) {
                            isDirectory = true;
                            path = Path(f);
                        }else{
                            System.out.println("文件路径:" + f.getPath());
                            isDirectory = false;
                        }
                    }

                    // -x 不break 因为要进行所有的操作
                case "-s":
                    if(isDirectory) {
                        // 输出文件名
                        System.out.println("文件路径：" + path[i]);
                    }else if(constant1.equals(s[0])){
                        System.out.println("【该路径为文件，-s无效】");
                    }
                    if(!constant2.equals(s[0])){
                        break;
                    }

                case "-c":
                    // 遍历出来的非目录文件名
                    if (isDirectory) {
                        countChar(path[i]);
                    }
                    // 不以"-x"开头，同时不是目录
                    else {
                        countChar(f.getPath());
                    }
                    if(!constant2.equals(s[0])){
                        break;
                    }

                case "-w":
                    if (isDirectory) {
                        countWord(path[i]);
                    }
                    else {
                        countWord(f.getPath());
                    }
                    if(!constant2.equals(s[0])) {
                        break;
                    }

                case "-l":
                    if (isDirectory) {
                        countRow(path[i]);
                    }
                    else {
                        countRow(f.getPath());
                    }
                    if(!constant2.equals(s[0])){
                        break;
                    }

                case "-a":
                    if (isDirectory) {
                        countElse(path[i]);
                    }
                    else {
                        countElse(f.getPath());
                    }
                    if(!constant2.equals(s[0])){
                        break;
                    }

                default:
                    if(constant2.equals(s[0])) {
                        break;
                    }
            }
            // 执行完file_path下的每一个文件的所有wc命令
            if (n == s.length - 1 && isDirectory) {
                count++;
                // 判断file_path下的文件是否全部结束？ 后面的条件是无限让GUI选择
                if (path[++i] == null && !constant2.equals(s[0])) {
                    break;
                }
                if(path[i] == null){
                    isEnd = true;
                }
                System.out.println("-------------------------");
                //  n=-1 下面的n++变成 n = 0，这样通过“-s”输出文件名
                n = -1;
            }
            if(!isDirectory && constant2.equals(s[0])){
                isEnd = true;
                n = -1;
            }
            n++;
        }
        System.out.println("----------------------");
        //统计
        System.out.println("总共" + count + "个文件");
    }

    /**    字符数，不含空格、回车、tab,   输出可显示字符       */
    public static void countChar(String s) throws Exception {
        FileReader in = new FileReader(s);
        int characterNum = 0;
        while (true) {
            int c = in.read();
            if (c == -1) {
                break;
            }
            if (c != '\r' && c != ' ' && c != '\n' && c != '\t') {
                characterNum++;
            }
        }
        System.out.println("文件字符数为" + characterNum);
        in.close();
    }

    /**     行数     readline()读行  差点，回车结束读不出来    */
    public static void countRow(String s) throws Exception {
        FileReader in = new FileReader(s);
        int rowNum = 1;
        int r = in.read();
        while (true) {
            // 接收的回车是  两个字节
            if(r == '\r') {
                if (in.read() == '\n') {
                    rowNum++;
                }
            }
            r = in.read();
            if (r == -1) {
                break;
            }
        }
        System.out.println("文件行数为" + rowNum);
        in.close();
    }

    /**       单词数，字母才认为一个单词，特殊字符分词         */
    public static void countWord(String s) throws Exception {
        FileReader in = new FileReader(s);
        int wordNum = 0;
        char nullChar = ' ';
        boolean flag = false;
        int w = in.read();
        while (true) {
            // 单词只记录 标识，其他字符组成不算单词
            boolean isWord = false;
            // 避免多个空格连续
            while (w == nullChar) {
                w = in.read();
            }
            //找到下一个空格前的都是同一个word
            t:
            while (w != nullChar && w != -1) {
                // 数字 不算单词
                boolean wordForm = (w >= 'A' && w <= 'Z') || (w >= 'a' && w <= 'z') || w == '$' || w == '_';
                if (wordForm) {
                    if (!isWord) {
                        isWord = true;
                    }
                } else {
                    w = in.read();
                    break t;
                }
                w = in.read();
            }
            //读到文本末尾，如果前面是字母，那么+1.
            flag = isWord;
            //出来的条件不是文件末尾，且是空格
            if (w != -1 && isWord) {
                wordNum++;
            }
            if (w == -1) {
                break;
            }
        }
        //worknum的计数是在遇到 空格、回车，计数前面连续的字符，若是最后读取-1，直接结束会漏掉一个
        if (flag){
            wordNum++;
        }
        System.out.println("文件单词数为" + wordNum);
        in.close();
    }

    /**          代码行、空行、注释行        */
    public static void countElse(String s) throws Exception {
        FileReader in = new FileReader(s);
        char nullChar = ' ', tabChar = '\t', rChar = '\r';
        int code = 0, empty = 0, note = 0, count;
        boolean isCode = false;
        boolean isNote = false;
        int t = in.read();
        while (true) {
            //  第一个可显示字符  null_str = ' '   tab_char = '\t'
            while (t == nullChar || t == tabChar) {
                t = in.read();
            }
            count = 0;
            // 非文尾 行末
            while (t != rChar && t != -1) {
                // 代码行，标识、代码 都是字母
                boolean judge1 = !isNote && !isCode && ((t >= 'A' && t <= 'Z') || (t >= 'a' && t <= 'z') || t == ';');
                if (judge1) {
                    code++;
                    isCode = true;
                }
                // 数可显示字符
                if (t != ' ' && t != '\t') {
                    count++;
                }
                // 多行注释（/*）和单行注释（//）
                boolean judge2 = t == '/' && !isNote && ((t=in.read())=='/'|| t=='*');
                if (judge2) {
                    isNote = true;
                    note++;
                }
                t = in.read();
            }              // while
            //  防止直接 回车空行      r_char='\r'
            if (t == rChar) {
                // 不大于1个可显示字符才算空行，且不是注释行，代码行
                if (!isCode && !isNote && count <= 1) {
                    empty++;
                }//  问题： count数到了注释行代表‘/’？
                // 未遇到 字母（代码行代表），注释行（'/'代表）       这种  })特殊
                else if (!isCode && !isNote) {
                    code++;
                }
                //  '\n'
                t = in.read();
                //下一行开头
                t = in.read();
            }

            // 到下一行，初始化
                isCode = false;
                isNote = false;
                // 到达这一步至少是 \r 回车后-1 肯定空行
                if (t == -1) {
                    if(!isCode && !isNote) {
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
    public static String[] Path(File f){
        int dirNum = 0;
        int mapKey = 0;
        String[] realPath = new String[1000];
        HashMap<Integer,File> hash = new HashMap<Integer,File>();
        File[] list = f.listFiles();
        while(dirNum < list.length) {
            // 拿到输入 路径下的所有文件
            hash.put(mapKey++, list[dirNum++]);
        }
        int count_dir = 0;
        int count ;
        int len = list.length;
        while (count_dir < len) {
            count = 0;
            File temp_file = hash.get(count_dir);
            if (temp_file.isDirectory()) {
                list = temp_file.listFiles();
                len += list.length;
                while (count < list.length) {
                    // 将该目录下的东西全部添加
                    hash.put(mapKey++, list[count++]);
                }
            }
            count_dir++;
        }
        int i =0 ,j=0;
        //  将hash中属于文件的拿出来作为路径
        while(i<hash.size()) {
            if(!hash.get(i).isDirectory()) {
                realPath[j++] = hash.get(i).getPath();
            }
            i++;
        }
        return realPath;
    }
}