<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Mr. Checkoff</h1>
        <h2>ToDo Lists:</h2>

        <div>
            <#if loggedIn>
                <span>Hello User! | </span>
                <a href="?cmd=logout">Log Out</a>
            <#else>
                <a href="?cmd=login">Log In | </a>
                <a href="?cmd=signup">Sign Up</a>
            </#if>
        </div>

        <table border="1">
            <tr>
                <th>
            </tr>
            <#list toDoList as toDoList>
            <tr>
                
            </tr>
            </#list>
        </table><br />
        <br />

        
    </body>
</html>