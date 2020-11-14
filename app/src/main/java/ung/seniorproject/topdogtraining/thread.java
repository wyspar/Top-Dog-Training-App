package ung.seniorproject.topdogtraining;

import android.content.Context;



public class thread{
   // private LinkedList<thread> allThreads = new LinkedList<thread>();
    private int threadID = 0;

    public thread(int id){
        threadID = id;
    }

    public int getThreadID(){
        return threadID;
    }


}
