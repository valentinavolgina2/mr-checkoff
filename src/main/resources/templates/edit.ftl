<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Edit</title>
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
        <h2>Edit List:</h2>

        <form action="?cmd=save" method="post">

            <#if todoList??>
                <input type="hidden" name="listID" value=${todoList.id?c} size=5 required/>
                <input type="text" name="description" value="${todoList.description}" placeholder="to-do list description" size=90 required/><br /><br />
            <#else>
                <input type="hidden" name="listID" value=-1 size=5 required/>
                To-do list description: <input type="text" name="description" value="" size=90 required/><br /><br />
            </#if>

            <#list items as item>

                <#assign itemName = "name${item?index}">
                <#assign itemCompleted = "completed${item?index}">
                <#assign itemId = "id${item?index}">

                <#if loggedIn>
                    <input type="hidden" name=${itemId} value=${item.id?c} size=5 required/>

                    <a href="?cmd=complete-item&itemId=${item.id?c}&listId=${todoList.id?c}">
                    <#if item.completed> undo <#else> complete </#if>
                    </a>
                <#else>
                    <input type="hidden" name=${itemId} value=${item?index} size=5 required/>

                    <a href="?cmd=complete-item&itemId=${item?index}&listId=${todoList.id?c}">
                    <#if item.completed> undo <#else> complete </#if>
                    </a>
                </#if>

                ${item.name}
                
                <#if loggedIn>
                    <a href="?cmd=delete-item&itemId=${item.id?c}&listId=${todoList.id?c}">delete item</a><br />
                <#else>
                    <a href="?cmd=delete-item&itemId=${item?index}&listId=${todoList.id?c}">delete item</a><br />
                </#if>

            </#list>


            <input type="hidden" name="count" value="${count}" size=10/><br /><br />

            <input type="submit" value="Save" />
            <input class="button" type="button" onclick="window.location.replace('/todo/lists')" value="Cancel" />
        </form><br /><br />

        <form action="?cmd=add-item" method="post">
            <#if todoList??>
                <input type="hidden" name="listID" value=${todoList.id?c} size=5 required/>
                <input type="text" name="newItem" value="" size=70 required/>
                <input type="submit" value="Add item" />
            </#if>
        </form><br />
    </body>
</html>
