package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/*
*类名和方法不能修改
 */
public class Schedule {



    public static class Node{
       public int mId=0;//节点ID

        public Node(int mId) {
            this.mId = mId;
        }
    }

    public static class Task{
         public int mTaskId=0;
         public int mConsumption=0;

        public Task(int taskId, int consumption) {
            this.mTaskId = taskId;
            this.mConsumption = consumption;
        }
    }
    //节点列表
    private List<Node> mNodeList= new ArrayList<Node>();
    private List<Task> mTaskList = new ArrayList<Task>();



    public int init() {
        // TODO 方法未实现
        mNodeList.clear();
        mTaskList.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        // TODO 方法未实现
        if(nodeId<=0){
            return ReturnCodeKeys.E004;
        }
        if(mNodeList.size()>0){
           for(Node node:mNodeList){
               if(node.mId==nodeId){
                return ReturnCodeKeys.E005;
               }
           }
        }
        Node node= new Node(nodeId);
        mNodeList.add(node);
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        // TODO 方法未实现
        if(nodeId<=0){
            return ReturnCodeKeys.E004;
        }
        boolean hasNode=false;
        if(mNodeList.size()>0){
            for(Node node:mNodeList){
                if(node.mId==nodeId){
                    hasNode=true;
                    mNodeList.remove(node);
                    break;
                }
            }
        }
        if(!hasNode){
            return ReturnCodeKeys.E007;
        }else{
            return ReturnCodeKeys.E006;
        }
    }


    public int addTask(int taskId, int consumption) {
        // TODO 方法未实现
        if(taskId<=0){
            return ReturnCodeKeys.E009;
        }
        boolean hasTheTask=false;
        if(mTaskList.size()>0){
            for(Task task:mTaskList){
                if(task.mTaskId==taskId){
                    hasTheTask=true;
                    break;
                }
            }
        }
        if(hasTheTask){
            return ReturnCodeKeys.E010;
        }else{
            Task task = new Task(taskId,consumption);
            mTaskList.add(task);
            return  ReturnCodeKeys.E008;
        }
    }


    public int deleteTask(int taskId) {
        // TODO 方法未实现
        if(taskId<=0){
           return ReturnCodeKeys.E009;
        }
        boolean hasTheTask=false;
        if(mTaskList.size()>0){
            for(Task task:mTaskList){
                if(task.mTaskId==taskId){
                    hasTheTask=true;
                    mTaskList.remove(task);
                    break;
                }
            }
        }
        if(!hasTheTask){
            return ReturnCodeKeys.E012;
        }else{
            return ReturnCodeKeys.E011;
        }
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }

}
