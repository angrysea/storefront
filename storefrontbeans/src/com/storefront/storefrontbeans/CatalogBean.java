package com.storefront.storefrontbeans;

import javax.servlet.*;
import java.sql.*;
import com.storefront.storefrontrepository.*;

public class JobBean extends BaseBean {

    final static private String fields[] = { "id", "description" };

    final static private String tablename = "catalog";

    public JobBean()
    {
        super();
    }

    String getdburl()
    {
        return "jdbc:mysql:///idata";
    }

    String[] getfields()
    {
        return fields;
    }

    String gettableName()
    {
        return tablename;
    }

    String getindexName()
    {
        return "code";
    }

    AddJobResponse AddJob(AddJobRequest request) throws ServletException
    {
        AddJobResponse response = null;

        try
        {
            pstmt = conn.prepareStatement(getInsertString());

            Job job = request.getJob();
            pstmt.setInt(1, job.getcode());
            pstmt.setString(2, job.getdescription());
            pstmt.setInt(3, job.getcustomer());
            int updateCount = pstmt.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ServletException(ex.getMessage());
        }

        return response;
    }

    GetJobsResponse GetJobs(GetJobsRequest request) throws ServletException
    {
        GetJobsResponse response = new GetJobsResponse();

        try
        {
            Job job = new Job();
            rs = stmt.executeQuery(getSelectString());
            while(rs.next())
            {
                job.setcode(rs.getInt(1));
                job.setdescription(rs.getString(2));
                job.setcustomer(rs.getInt(3));
                response.setJob(job);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ServletException(ex.getMessage());
        }
        return response;
    }

    GetJobResponse GetJob(GetJobRequest request) throws ServletException
    {
        GetJobResponse response = new GetJobResponse();

        try
        {
            Job job = new Job();
            rs = stmt.executeQuery(getSelectString("id="+Integer.toString(request.getcode())));
            if(rs.next())
            {
                job.setcode(rs.getInt(1));
                job.setdescription(rs.getString(2));
                job.setcustomer(rs.getInt(3));
                response.setJob(job);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ServletException(ex.getMessage());
        }
        return response;
    }

    public static void main(String args[])
    {
        JobBean bean = new JobBean();
        try
        {
            bean.init();
            AddJobRequest request = new AddJobRequest();
            Job job = new Job();
            job.setcode(1);
            job.setdescription("Bank of America (AMG)");
            job.setcustomer(1);
            AddJobResponse response = bean.AddJob(request);

            GetJobRequest request2 = new GetJobRequest();
            request2.setcode(1);
            GetJobResponse response2 = bean.GetJob(request2);
            bean.destroy();
        }
        catch(Exception e)
        {
        }
    }
}
