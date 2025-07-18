package Pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User
{
    String name;
    String job;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }
}
