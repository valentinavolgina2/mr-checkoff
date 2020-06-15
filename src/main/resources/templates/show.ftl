<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Show</title>
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
        <h2>${todoList.description}</h2>
    
        <a href="?cmd=edit&id=${todoList.id?c}">Edit list</a><br>
        <a href="?cmd=delete&id=${todoList.id?c}">Delete list</a>
        <br><br>

        <table border="1">
            <tr>
                <th>Item Name</th>
            </tr>
            <#list items as item>
            <tr>
                <td><input type="checkbox"/>${item.name}</td>
            </tr>
            </#list>
        </table><br />
    </body>
</html>
