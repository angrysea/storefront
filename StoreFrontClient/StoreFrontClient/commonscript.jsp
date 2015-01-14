    <script language="JavaScript">
        function strltrim() {
            return this.replace(/^\s+/,'');
        }
        function strrtrim() {
            return this.replace(/\s+$/,'');
        }
        function strtrim() {
            return this.replace(/^\s+/,'').replace(/\s+$/,'');
        }
        String.prototype.ltrim = strltrim;
        String.prototype.rtrim = strrtrim;
        String.prototype.trim = strtrim;
    </script>

    <script language="JavaScript">
        function checkEmail(emailaddress)
        {
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(emailaddress))
            {
                return (true)
            }
            alert("Invalid E-mail Address! Please re-enter.")
            return (false)
        }
    </script>

    <script language="JavaScript">
        function isValid(string,allowed)
        {
            for (var i=0; i< string.length; i++)
            {
                if (allowed.indexOf(string.charAt(i)) == -1)
                    return false;
            }
            return true;
        }
    </script>
