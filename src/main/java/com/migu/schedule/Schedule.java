package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

/*
*类名和方法不能修改
 */
public class Schedule {



    public static class Node{
       public int mId=0;//节点ID

        public Node(int mId) {
            this.mId = mId;
        }
       //运行任务对列；
        public List<Task> mRunningTask=new ArrayList<Task>();

        public boolean hasTask(int taskId){
            boolean result=false;
            if(mRunningTask.size()>0){
                for(Task task:mRunningTask){
                    if(task.mTaskId==taskId){
                        result=true;
                        break;
                    }
                }
            }
            return result;
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
    //挂起的任务队列；
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
                    List<Task> runningTasks= node.mRunningTask;
                    if(runningTasks.size()>0){
                        mTaskList.addAll(runningTasks);
                        runningTasks.clear();
                    }
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
        //节点上的运行任务查寻
        if(!hasTheTask){
            if(mNodeList.size()>0){
                for(Node node:mNodeList){
                    List<Task> runningTask=node.mRunningTask;//节点上运行任务队列；
                    if(runningTask.size()>0){
                        for(Task nodeTask:runningTask){
                            if(nodeTask.mTaskId==taskId){
                                hasTheTask=true;
                                runningTask.remove(nodeTask);
                                break;
                            }
                        }
                    }
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
        if(threshold<=0){
            return ReturnCodeKeys.E002;
        }
       int nodeSize= mNodeList.size();
       int taskSize= mTaskList.size();
       int totalConsuption= 0;
       List<Integer> consumptionList= new ArrayList<Integer>();

       for(int i=0;i<taskSize;i++){
           Task task= mTaskList.get(i);
           totalConsuption+=task.mConsumption;
           consumptionList.add(new Integer(task.mConsumption));
       }
//        Collections.sort(consumptionList);
//       int count=taskSize/nodeSize;
//      int[][] averages= new int[nodeSize][];
//       for(int i=0;i<taskSize;i++){
//               averages[i%count][i/count]=consumptionList.get(i);
//       }
//       /*
//      iint[] sum=new int[nodeSize];
//       for(int i=0;i<averages.length;i++){
//
//       }*/
        return ReturnCodeKeys.E014;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        if(null==tasks){
            return ReturnCodeKeys.E016;
        }
        int size=tasks.size();
        List<TaskInfo> des = new  ArrayList(Arrays.asList( new  Object[size]));
        Collections.copy(des,tasks);
        tasks.clear();
        //taskInfo map
        Map<Integer,Integer> map= new HashMap<Integer,Integer>();
        //TaskId list
        List<Integer> keys= new ArrayList<>();
        for(TaskInfo taskInfo:des){
            //服务节点的运行任务队列中查找；
            boolean hasTaskInNode=false;//任务队列查找到该任务的标志位
            if(mNodeList.size()>0){
                for(Node node :mNodeList){
                   hasTaskInNode = node.hasTask(taskInfo.getTaskId());
                   if(hasTaskInNode){
                       taskInfo.setNodeId(node.mId);
                       break;
                   }
                }
            }
            //在挂起任务队列中查找。
            if(!hasTaskInNode&&mTaskList.size()>0){
                for(Task task:mTaskList){
                    if(task.mTaskId==taskInfo.getTaskId()){
                        taskInfo.setNodeId(-1);
                        break;
                    }
                }
            }
            Integer key= new Integer(taskInfo.getTaskId());
            Integer value= new Integer(taskInfo.getNodeId());
            keys.add(key);
            map.put(key,value);
        }

       Collections.sort(keys);
        List<TaskInfo> result = new ArrayList<TaskInfo>();
        for(Integer key:keys){
            int value=  map.get(key);
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskId(key);
            taskInfo.setNodeId(value);
            result.add(taskInfo);
        }
        tasks=result;
        return  ReturnCodeKeys.E015;


    }

}
