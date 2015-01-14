package com.ibm.network.ftp;

import java.io.*;
import java.util.*;

public class FileInfo
    implements Serializable
{

    private String fileName;
    private String size;
    private String date;
    private String time;
    private boolean offsetByOne;
    private String reply;
    private boolean file;
    private static final String JANUARY = "JAN";
    private static final String FEBRUARY = "FEB";
    private static final String MARCH = "MAR";
    private static final String APRIL = "APR";
    private static final String MAY = "MAY";
    private static final String JUNE = "JUN";
    private static final String JULY = "JUL";
    private static final String AUGUST = "AUG";
    private static final String SEPTEMBER = "SEP";
    private static final String OCTOBER = "OCT";
    private static final String NOVEMBER = "NOV";
    private static final String DECEMBER = "DEC";

    public String getReply()
    {
        return reply;
    }

    public FileInfo()
    {
        fileName = "";
        size = "";
        date = "";
        time = "";
    }

    public String getDate()
    {
        return date;
    }

    public String getName()
    {
        return fileName;
    }

    public String getSize()
    {
        return size;
    }

    public String getTime()
    {
        return time;
    }

    public boolean isFile()
    {
        return file;
    }

    private boolean isMonth(String s)
    {
        return s.equalsIgnoreCase("JAN") || s.equalsIgnoreCase("FEB") || s.equalsIgnoreCase("MAR") || s.equalsIgnoreCase("APR") || s.equalsIgnoreCase("MAY") || s.equalsIgnoreCase("JUN") || s.equalsIgnoreCase("JUL") || s.equalsIgnoreCase("AUG") || s.equalsIgnoreCase("SEP") || s.equalsIgnoreCase("OCT") || s.equalsIgnoreCase("NOV") || s.equalsIgnoreCase("DEC");
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setFile(boolean flag)
    {
        file = flag;
    }

    public void setLocalDescription(String s, String s1)
    {
        File file1 = new File(s1, s);
        setName(s);
        setFile(file1.isFile());
        Long long1 = new Long(file1.length());
        setSize(long1.toString());
        Date date1 = new Date(file1.lastModified());
        StringTokenizer stringtokenizer = new StringTokenizer(date1.toString());
        stringtokenizer.nextToken();
        setDate(stringtokenizer.nextToken() + " " + stringtokenizer.nextToken());
        setTime(stringtokenizer.nextToken());
    }

    public void setName(String s)
    {
        fileName = s;
    }

    public void setRemoteDescriptionMVS(String s)
    {
        reply = s;
        Object obj = null;
        Object obj1 = null;
        if(s != null)
        {
            try
            {
                StringTokenizer stringtokenizer = new StringTokenizer(s);
                setFile(true);
                stringtokenizer.nextToken();
                if(s.toUpperCase().trim().startsWith("MIGRATED"))
                {
                    setFile(false);
                    setName(stringtokenizer.nextToken());
                    return;
                }
                String s1 = stringtokenizer.nextToken();
                int i = 0;
                if(s1.equals("3380"))
                {
                    i = 49152;
                } else
                if(s1.equals("3390"))
                {
                    i = 53248;
                } else
                if(!s1.equals("Tape"))
                {
                    setFile(false);
                    setSize("TAPE");
                    setName(stringtokenizer.nextToken());
                    return;
                }
                if(stringtokenizer.countTokens() == 3 && s1.equals("Tape"))
                {
                    setName(stringtokenizer.nextToken());
                    return;
                }
                if(stringtokenizer.countTokens() == 3)
                {
                    setFile(false);
                    setSize("DIR");
                    setName(stringtokenizer.nextToken());
                    return;
                }
                if(!s1.equals("Tape"));
                if(stringtokenizer.countTokens() == 2)
                {
                    stringtokenizer.nextToken();
                    setFile(false);
                    setName(stringtokenizer.nextToken());
                    return;
                }
                setDate(stringtokenizer.nextToken());
                setTime("");
                String s2 = "0";
                if(stringtokenizer.countTokens() == 7)
                {
                    stringtokenizer.nextToken();
                    String s3 = stringtokenizer.nextToken();
                } else
                if(stringtokenizer.countTokens() == 6)
                {
                    String s5 = stringtokenizer.nextToken();
                    String s4 = s5.substring(s5.length() - 5);
                } else
                {
                    return;
                }
                setSize("0000");
                stringtokenizer.nextToken();
                stringtokenizer.nextToken();
                stringtokenizer.nextToken();
                stringtokenizer.nextToken();
                setName(stringtokenizer.nextToken());
            }
            catch(Throwable throwable)
            {
                System.out.println(throwable.getMessage());
                fileName = null;
                size = null;
                date = null;
                time = null;
            }
        }
    }

    public void setRemoteDescriptionUNIX(String s)
    {
        reply = s;
        offsetByOne = false;
        Object obj = null;
        Object obj1 = null;
        if(s != null)
        {
            try
            {
                StringTokenizer stringtokenizer = new StringTokenizer(s);
                String s1 = stringtokenizer.nextToken();
                if(s1.charAt(0) == 'd')
                {
                    setFile(false);
                } else
                if(s1.charAt(0) == '-')
                {
                    setFile(true);
                }
                stringtokenizer.nextToken();
                stringtokenizer.nextToken();
                String s3 = stringtokenizer.nextToken();
                String s4 = stringtokenizer.nextToken();
                try
                {
                    Integer.parseInt(s4);
                }
                catch(RuntimeException runtimeexception)
                {
                    offsetByOne = true;
                }
                if(offsetByOne)
                {
                    setSize(s3);
                } else
                {
                    setSize(s4);
                }
                if(!isFile())
                {
                    setSize("DIR");
                }
                String s5;
                if(offsetByOne)
                {
                    s5 = s4;
                } else
                {
                    s5 = stringtokenizer.nextToken();
                }
                String s2 = new String(s5 + "  " + stringtokenizer.nextToken());
                setDate(s2);
                StringTokenizer stringtokenizer1 = new StringTokenizer(stringtokenizer.nextToken(), ":");
                if(stringtokenizer1.countTokens() == 2)
                {
                    setTime(stringtokenizer1.nextToken() + ":" + stringtokenizer1.nextToken());
                    Calendar calendar = Calendar.getInstance();
                    int j = calendar.get(1);
                    setDate(j + " " + s2);
                } else
                {
                    setTime("00:00");
                    int i = Integer.parseInt(stringtokenizer1.nextToken());
                    setDate(i + " " + s2);
                }
                String s6 = stringtokenizer.nextToken("").substring(1).trim();
                if(s6.indexOf("->") >= 0)
                {
                    s6 = s6.substring(0, s6.indexOf("->"));
                    for(int k = s6.length() - 1; k > 0; k--)
                    {
                        if(s6.charAt(k) != '-')
                        {
                            break;
                        }
                        s6 = s6.substring(0, k - 1);
                    }

                    s6 = s6.trim();
                }
                setName(s6);
            }
            catch(Throwable throwable)
            {
                fileName = "";
                size = "";
                date = "";
                time = "";
            }
        }
    }

    public void setRemoteDescriptionVM(String s)
    {
        reply = s;
        Object obj = null;
        Object obj1 = null;
        if(s != null)
        {
            try
            {
                StringTokenizer stringtokenizer = new StringTokenizer(s);
                setFile(true);
                setName(stringtokenizer.nextToken() + "." + stringtokenizer.nextToken());
                stringtokenizer.nextToken();
                setSize(stringtokenizer.nextToken());
                stringtokenizer.nextToken();
                stringtokenizer.nextToken();
                setDate(stringtokenizer.nextToken());
                setTime(stringtokenizer.nextToken());
            }
            catch(Throwable throwable)
            {
                fileName = null;
                size = null;
                date = null;
                time = null;
            }
        }
    }

    public void setRemoteDescriptionWinNT(String s)
    {
        reply = s;
        if(s.length() < 40)
        {
            setName("BAD FILE: " + s);
            return;
        }
        String s1 = s.substring(0, 9);
        String s2 = s.substring(11, 18);
        String s3 = s.substring(24, 30);
        String s4 = s.substring(19, 38);
        String s5 = s.substring(39);
        setName(s5.trim());
        if(s3.indexOf("<DIR>") >= 0)
        {
            setFile(false);
        } else
        {
            setFile(true);
            setSize(s4.trim());
        }
        setDate(s1);
        setTime(s2);
    }

    public void setRemoteDescriptionOS400(String s)
    {
        reply = s;
        StringTokenizer stringtokenizer = new StringTokenizer(s);
        stringtokenizer.nextToken();
        String s1 = stringtokenizer.nextToken();
        if(s1.equals("*MEM"))
        {
            setName(stringtokenizer.nextToken());
            return;
        } else
        {
            setSize(s1);
            setDate(stringtokenizer.nextToken());
            setTime(stringtokenizer.nextToken());
            stringtokenizer.nextToken();
            setName(stringtokenizer.nextToken());
            return;
        }
    }

    public void setSize(String s)
    {
        size = s;
    }

    public void setTime(String s)
    {
        time = s;
    }
}
