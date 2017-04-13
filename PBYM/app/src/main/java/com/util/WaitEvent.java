package com.util;

public class WaitEvent
{
    public final static int ERROR_OTHER = 2;
    public final static int ERROR_TIME_OUT = 1;
    public final static int SUCCESS = 0;

    private Object mSignal;
    private boolean mFlag;
    private int mResult;

    private MyThread myThread;

    public WaitEvent()
    {
        mSignal = new Object();
        mFlag = true;
        mResult = SUCCESS;
    }
    public void Init()
    {
        mFlag = true;
        mResult = SUCCESS;
    }
    public int waitSignal(int millis)
    {
        myThread = new MyThread();
        myThread.startThread(millis);
        if(!mFlag)
            return mResult;

        synchronized (mSignal)
        {
            try
            {
                mSignal.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        return mResult;
    }

    public void setSignal(boolean bSuccess)
    {
        synchronized (mSignal)
        {
            mFlag = false;
            if(!bSuccess)
                mResult = ERROR_OTHER;
            if(myThread != null)
                myThread.stopThread();
            mSignal.notify();
        }
    }

    private void waitTimeOut()
    {
        mResult = ERROR_TIME_OUT;
        setSignal(true);
    }

    class MyThread extends Thread
    {
        boolean mThreadAlive = false;
        int mCount = 0;
        int mTotal = 0;
        public void startThread(int millis)
        {
            mTotal = millis/10;
            mCount = 0;
            mThreadAlive = true;
            start();
        }

        public void stopThread()
        {
            mThreadAlive = false;
        }

        @Override
        public void run()
        {
            while(true)
            {
                try
                {
                    mCount++;
                    Thread.sleep(10);

                    if(!mThreadAlive)
                    {
                        return ;
                    }

                    if(mCount > mTotal)		//超时
                    {
                        waitTimeOut();
                        return ;
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
