package kr.hs.sugong.simplenote.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016-03-13.
 */
public class FileUtil {


    public static boolean saveObjectFile(String filename, Serializable data) throws IOException {
//
        FileOutputStream fo = new FileOutputStream(filename);
        ObjectOutputStream oo = new ObjectOutputStream(fo);

        oo.writeObject(data);
        oo.flush();
        oo.close();

        return true;
    }

    public static List readObjectFile(String filename) throws ClassNotFoundException, IOException {
//      외부저장소로 내보내기
        FileInputStream fio = new FileInputStream(filename);
        ObjectInputStream oi = new ObjectInputStream(fio);
//      내용을 순차적으로 넣기 위해 사용
        LinkedList list = new LinkedList();
        Object item = null ;

//      외부저장소에 저장된 DB파일을 순차적으로 읽어 넣는다.
        while ((item = oi.readObject()) != null) {
            list.add(item);
        }
        return list;
    }


}
