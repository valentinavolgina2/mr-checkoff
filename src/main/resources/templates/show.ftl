<!DOCTYPE html>
<html>
    <head>
        <title>Display Item</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Item Viewer</h1>
        <h2>${item.name}</h2>

        <a href="?cmd=show&index=${prevIndex}">Previous</a> &nbsp; &nbsp;
        <a href="?cmd=show&index=${nextIndex}">Next</a><br/>
    </body>
</html>
