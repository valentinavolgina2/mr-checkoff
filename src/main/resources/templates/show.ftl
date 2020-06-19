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

        <h2>
        ${todoList.description} | 
        <a href="?cmd=edit&id=${todoList.id?c}">Edit</a>
        <a href="?cmd=delete&id=${todoList.id?c}">Delete</a>
        </h2>

        <form action="?cmd=add-item" method="post">
            <#if todoList??>
                <input type="hidden" name="listID" value=${todoList.id?c} size=5 required/>
                <input type="text" name="newItem" value="" size=35 required/>
                <input type="submit" value="Add item" />
            </#if>
        </form><br />

        <br>

        <table border="1">
            <tr>
                <th> </th><th> </th><th>Item Name</th><th>Action</th>
            </tr>
            <#list items as item>

                <#if loggedIn>
                    <#assign itemId = item.id?c>
                <#else>
                    <#assign itemId = item?index?c>
                </#if>

                <tr>
                    <td>${item?index + 1}</td>
                    <td>
                        <input type="hidden" name="id" value=itemId required/>

                        <a href="?cmd=complete-item&itemId=${itemId}&listId=${todoList.id?c}">
                        <#if item.completed> undo <#else> complete </#if>
                        </a>
                    </td>
                    <td>${item.name}</td>
                    <td>
                        <a href="?cmd=delete-item&itemId=${itemId}&listId=${todoList.id?c}">delete</a>
                    </td>
                </tr>
            </#list>   

        </table><br />
        
    </body>
</html>
