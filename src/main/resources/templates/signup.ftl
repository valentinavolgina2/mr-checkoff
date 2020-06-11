<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Sign Up</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            Already signed up? Log in
        </div>

        <h1>Mr. Checkoff</h1>
        <h2>Create an account:</h2>

        <form action="?cmd=signup" method="post">

            Email: <input type="text" name="username" size=60 /><br />
            Password: <input type="text" name="password" size=60 /><br />

            <input type="submit" value="Submit" />
            <input class="button" type="button" value="Cancel" />
        </form><br />
    </body>
</html>
