<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Sign Up</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            Already signed up? <a href="?cmd=login">Log In</a>
        </div>

        <h1>Mr. Checkoff</h1>
        <h2>Create an account:</h2>

        <form action="?cmd=signup" method="post">

            First Name: <input type="text" name="firstName" size=60 required/><br />
            Last Name: <input type="text" name="lastName" size=60 /><br />
            Email: <input type="text" name="email" size=60 required/><br />
            Username: <input type="text" name="username" size=60 required/><br />
            Password: <input type="text" name="password" size=60 required/><br />

            <input type="submit" value="Submit" />
            <input class="button" type="button" onclick="window.location.replace('/todo/lists')" value="Cancel" />
        </form><br />
    </body>
</html>
