<!DOCTYPE html>
<html>
    <head>
        <title>Mr. Checkoff - Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            <#if loggedIn>
                Hello ${member.firstName}! | <a href="?cmd=logout">Log Out</a>
            <#else>
                <a href="?cmd=login">Log In</a> | <a href="?cmd=sigup">Sign Up</a>
            </#if>
        </div>

        <h1>Mr. Checkoff</h1>
        <h2>ToDo Lists:</h2>

        <table border="1">
            <tr>
                <th>List Name</th>
            </tr>
            <#list todoLists as todoList>
            <tr>
                <td><input type="checkbox"/><a href="?cmd=show&id=${todoList?id}">${todoList.description}</a></td>
            </tr>
            </#list>
        </table><br />

    </body>
</html>
