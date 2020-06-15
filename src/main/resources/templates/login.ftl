<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            Don't have an account? <a href="?cmd=signup">Sign Up</a>
        </div>

        <#if loggedIn>
            <h1><a href="?cmd=home">Mr.Checkoff</a></h1>
            <h2>Log in to your account:</h2>
        <#else>
            <form action="?cmd=login" method="post">

                Email: <input type="email" name="username" size=60 /><br />
                Password: <input type="password" name="password" size=60 /><br />

                <input type="submit" value="Submit" />
                <input class="button" type="button" value="Cancel" />
            </form><br />
        </#if>
    </body>
</html>
