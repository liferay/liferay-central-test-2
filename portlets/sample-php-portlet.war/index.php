<h3>Welcome to the PHP test portlet</h3>

Post parameters: <?php print_r($_POST); ?><br/>

Get parameters: <?php print_r($_GET); ?><br/>

<form action="index.php" method="post">
Foo: <input type="text" value="<?php echo $_GET["foo"]?$_GET["foo"]:$_POST["foo"]; ?>">
<input type="submit" />
</form>

<?php include("navigation.php"); ?>
