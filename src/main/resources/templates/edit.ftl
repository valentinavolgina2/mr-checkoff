<!DOCTYPE html>
<html>
    <head>
        <title>Mr.Checkoff - Edit</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div style="float: right">
            Log In | Sign Up
        </div>

        <h1>Mr. Checkoff</h1>
        <h2>Add / Edit List:</h2>
        <h3>${todoList.description}</h3>

        <table border="1">
            <tr>
                <th>Item Name</th>
            </tr>
            
            <tr>
                <td><input type="checkbox"/>${item.description}</td>
            </tr>
            
        </table><br />
    </body>
</html>
