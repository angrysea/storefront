<script type="text/javascript" language="JavaScript">
    function ValidateCreditCard(s)
    {
        // remove non-numerics
        var v = "0123456789";
        var w = "";
        for (i=0; i < s.length; i++)
        {
            x = s.charAt(i);
            if (v.indexOf(x,0) == -1)
            {
                alert("Please enter only numbers in the credit card field, spaces and dashes are invalid.");
                return false;
            }
            if (v.indexOf(x,0) != -1)
            w += x;
        }
        // validate number
        j = w.length / 2;
        if (j < 6.5 || j > 8 || j == 7)
        {
            alert("The credit card number entered is invalid.");
            return false;
        }
        k = Math.floor(j);
        m = Math.ceil(j) - k;
        c = 0;
        for (i=0; i<k; i++)
        {
            a = w.charAt(i*2+m) * 2;
            c += a > 9 ? Math.floor(a/10 + a%10) : a;
        }
        for (i=0; i<k+m; i++)
            c += w.charAt(i*2+1-m) * 1;
        if(c%10 != 0)
        {
            alert("The credit card number entered is invalid.");
            return false;
        }
        return true;
    }
</script>
