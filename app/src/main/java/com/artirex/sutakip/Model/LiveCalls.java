package com.artirex.sutakip.Model;

public class LiveCalls {

    private String pbx_num;

    private String crm_id;

    private Kuyruk[] queues;

    public String getPbx_num ()
    {
        return pbx_num;
    }

    public void setPbx_num (String pbx_num)
    {
        this.pbx_num = pbx_num;
    }

    public String getCrm_id ()
    {
        return crm_id;
    }

    public void setCrm_id (String crm_id)
    {
        this.crm_id = crm_id;
    }

    public Kuyruk[] getQueues ()
    {
        return queues;
    }

    public void setQueues (Kuyruk[] queues)
    {
        this.queues = queues;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pbx_num = "+pbx_num+", crm_id = "+crm_id+", queues = "+queues+"]";
    }




}