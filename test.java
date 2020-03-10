import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class test {
    public static void main(String[] args) throws Exception{
        File f = new File("e:/develop/theme/My_wc/wc/src/test.java");
//        Vector v =new Vector(10,10);
//        Vector vv = new Vector(10,10);
//        vv = fileDir(f,v);
//        String[] path = new String[20];
//        path = pathtest(f,vv);
//        int i = 1;
//        while( 0 < path.length) {
//            System.out.println(path[i]);
//            i++;
//        }

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
//        int num = 1;
//        FileReader in = new FileReader(f);
//        BufferedReader reader = new BufferedReader(in);
//        String str = reader.readLine();
//        while(str != null){
//            num++;
//            str = reader.readLine();
//        }
//        System.out.println(num);
    }
    public static Vector fileDir(File dir,Vector v){
        File[] files=dir.listFiles();         /*    listFiles()返回该目录下的所有文件与目录    */
        for(File filess : files){             /*   有子目录(含文件或目录)则递归直到得到所有文件名和目录名  */
            if(filess.isDirectory()){
                v.add(filess.getName());
                fileDir(filess,v);                                //递归
            }
            if(!filess.isDirectory())
                v.add(filess.getName());
        }
        v.add(" ");
        return v;
    }
    public static String[] pathtest(File f,Vector file_name){
        Object[] all_file = file_name.toArray();
        int i = 0 , j = 0;
        String[] real_path = new String[20];     // 固定长度文件...哎
        String temp1 = new String("");
        String temp2 = new String("");
        File turn_path = new File(f.getPath());
        while(i < all_file.length) {

            if (turn_path.isDirectory()) {
                if (!all_file[i].equals(" ")) {        // 不是以“ ”进来被认为的目录
                    if (!turn_path.canRead())
                        ;
                    else turn_path = new File(turn_path.getPath() + "\\" + all_file[i].toString());
                }
            }else             //  文件
                real_path[j++] = f.getPath() + temp1 + "\\" + all_file[i].toString();
            i++;
        }

//        File turn_path = new File(f.getPath() + "\\" + all_file[i].toString());
//        while(true) {
//            while (turn_path.isDirectory()) {
//                if (!all_file[i].equals(" ")) {
//                    temp = temp + "\\" + all_file[i++].toString();
//                    if (i < all_file.length)
//                        turn_path = new File(f.getPath() + temp + "\\" + all_file[i].toString());
//                    else break;
//                } else {
//                    temp = "";
//                    turn_path = new File(f.getPath() + temp + "\\" + all_file[i].toString());
//                }
//            }
//            real_path[j++] = f.getPath() + temp + "\\" + all_file[i].toString();
//            if( ++i < all_file.length)
//                turn_path = new File(f.getPath() + temp + "\\" + all_file[i].toString());
//            else break;
//
        return real_path;
   }
}

