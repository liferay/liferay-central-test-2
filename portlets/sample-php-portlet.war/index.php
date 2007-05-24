Welcome to the PHP test portlet.

Post parameters: <?php echo $_POST; ?><br/>

Get parameters: <?php echo $_GET; ?><br/>

<form action="index.php" method="post">
Foo: <input type="text" value="<?php echo $_GET["foo"]?$_GET["foo"]:$_POST["foo"]; ?>">
<input type="submit" />
</form>

<?php include("navigation.php"); ?>
