package com.migu.schedule.info;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.reflect.Array.*;

public class MyTest {

    private static void log(List list){
        for(int i=0;i<list.size();i++){
            System.out.println("-----"+list.get(i));
        }
    }


    public static  void main(String[] args){
        List<String> mainList= new ArrayList<String>();
        List<String> subList= new ArrayList<String>();

        for(int i=0;i<3;i++){
            mainList.add(i+"");
        }

        for(int i=10;i>9;i--){
            subList.add(i+"");
        }
        //log(subList);
        mainList.addAll(subList);
        log(mainList);
        int size=mainList.size();

        List des = new  ArrayList(Arrays.asList( new  Object[size])); // 注意：new ArrayList(Collection col)参数必须要实现Collection 接口。


        System.out.println("-----------");
        Collections.copy(des,mainList);
        mainList.clear();
        log(des);
    }
}
