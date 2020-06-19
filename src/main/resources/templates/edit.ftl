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

        <form action="?cmd=save" method="post">

            <#if todoList??>
                <h2>New To-Do List:</h2>
                <input type="hidden" name="listID" value=${todoList.id?c} size=5 required/>
                <input type="text" name="description" value="${todoList.description}" placeholder="to-do list description" size=90 required/><br /><br />
            <#else>
                <h2>Edit To-Do List description:</h2>
                <input type="hidden" name="listID" value=-1 size=5 required/>
                To-do list description: <input type="text" name="description" value="" size=90 required/><br /><br />
            </#if>


            <input type="submit" value="Save" />
            <input class="button" type="button" onclick="window.location.replace('/todo/lists')" value="Cancel" />
        </form><br /><br />

    </body>
</html>
