<!DOCTYPE html>
<html>
    <head>
        <title>Mr. Checkoff - Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            Log In | Sign Up
        </div>

        <h1>Mr. Checkoff</h1>
        <h2>ToDo Lists:</h2>

        <table border="1">
            <tr>
                <th>List Name</th>
            </tr>
            <#list todoLists as todoList>
            <tr>
                <td><input type="checkbox"/>${todoList.description}</td>
            </tr>
            </#list>
        </table><br />

    </body>
</html>
