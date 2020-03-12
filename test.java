import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


public class test {
    public static void main(String[] args) throws Exception{
//        File f = new File("e:/develop/theme/My_wc/t.txt");
//        int dir_num = 0, map_key = 0;
//        String[] real_path = new String[10];
//        HashMap<Integer,File> hm = new HashMap<Integer,File>();
//        File[] list = f.listFiles();
//        while(dir_num < list.length)
//                hm.put(map_key++,list[dir_num++]);       // 拿到输入 路径下的所有文件
//        int count_dir = 0, count ,len = list.length;
//            while (count_dir < len) {
//                count = 0;
//                File temp_file = hm.get(count_dir);
//                if (temp_file.isDirectory()) {
//                    list = temp_file.listFiles();
//                    len += list.length;
//                    while (count < list.length)
//                        hm.put(map_key++, list[count++]);
//                }
//                count_dir++;
//            }
//            int i =0 ,j=0;
//            while(i<hm.size()) {
//                if(!hm.get(i).isDirectory())
//                   real_path[j++] = hm.get(i).getPath();
//                i++;
//            }
//            i = 0;
//            while(i < real_path.length)
//                System.out.println(real_path[i++]);

//        String[] s;
//        int i=0,j=0;
//        enter = f.listFiles();
//        real_path = enter[0].listFiles();
//        real_path = enter[1].listFiles();


//        System.out.println(real_path[0]);
   //     System.out.println(real_path[1]);


//         while(i<real_path.length)                 // 目录下东西的路径
//            System.out.println(real_path[i++]);

//         s = f.list();                      // 目录下的东西
//         while(i<s.length)
//             System.out.println(s[i++]);
//        String str = f.getParent();
//        System.out.println(str);

//    if(f.canRead())         // e:/develop/theme/My_wc/out/wc  e:/develop/theme/My_wc/wc/test.java
//        System.out.println("ke");
//    else System.out.println("buke");

//        if(f.isDirectory())
//            System.out.println("目录");
//        else System.out.println("文件");

//        Object[] b = vv.toArray();
//        i = 0;
//        while(i < b.length){
//            System.out.println(b[i].toString());
//            i++;
//        }
//        if(f.isDirectory())
//            System.out.println("目录");
//        else System.out.println("文件");
//        if(path[0] == null)
//            System.out.println("null是空");
//        else System.out.println("null");

//        int num = 0;
//        FileReader in = new FileReader("e:/develop/theme/My_wc/wc/src/test.java");
//        BufferedReader reader = new BufferedReader(in);
//        int len = 0; String s;
//        s = reader.readLine();
//        while(s != null) {
//  //          if(s.contains("\r\n"))
//                len ++;
//            System.out.println(s);
//            s = reader.readLine();
//        }
//
//        System.out.println(len);

       JFileChooser jf = new JFileChooser();
       JTextArea

//            num++;
//        System.out.println(num);
//        String s = "e:/develop/theme/My_wc/t.txt";
//        FileReader in = new FileReader(s);
//        int t =in.read();
//        if(t == '\r') {
//                System.out.println("\t是-1");
//            t = in.read();
//            if(t=='\n')
//                System.out.println("ad");
//            t= in.read();
//            if(t=='\r') System.out.println("en");
//            if (t == -1)
//                System.out.println("\n是-1");
//        }
//        System.out.println("?");
//        int row_num = 1;
//        int r = in.read();
//        while (true) {
//            // 接收的回车是  两个字节
//            if(r == '\r') {
//                // r = in.read()
//                if (in.read() == '\n') {
//                    row_num++;
//                }
//            }
//                r = in.read();
//                if (r == -1) {
//                    break;
//                }
//        }


//        String str = reader.readLine(),string="$";
//        while(str!=null) {
//            string = str;
//            str = reader.readLine();
//            row_num++;
//        }
//        if(string.contains(""))
//            row_num++;
//
//        System.out.println("文件行数为" + row_num);
//        in.close();
    //    reader.close();
    }


    public static File[] fileDir(File dir, File[] f) {

        File[] files = dir.listFiles();         /*    listFiles()返回该目录下的所有文件与目录    */
        for (File filess : files) {             /*   有子目录(含文件或目录)则递归直到得到所有文件名和目录名  */
            if (filess.isDirectory())
                fileDir(filess, f);                                //递归
             else
                f[f.length] = filess;
        }
        return f;
    }


}
