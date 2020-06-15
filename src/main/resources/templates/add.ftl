<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Add</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            <#if loggedIn>
                Hello ${member.firstName}! | <a href="?cmd=logout">Log Out</a>
            <#else>
                <a href="?cmd=login">Log In</a> | <a href="?cmd=signup">Sign Up</a>
            </#if>
        </div>

        <h1><a href="?cmd=home">Mr.Checkoff</a></h1>
        <h2>Add New List:</h2>
        <br>

        Enter List Name:<input type="text" name="listName" size="60"/><br><br>
        <table border="1">
            <tr>
                <th>Item Name</th>
            </tr>
            <#list item.listID as item>
            <tr>
                <td><${item.description}</td>
            </tr>
            </#list>
        </table><br />
    </body>
</html>
